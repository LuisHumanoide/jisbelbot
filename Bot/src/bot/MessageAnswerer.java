/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.logic.Msg;
import bot.users.Users;
import bot.logic.Rule;
import bot.logic.RulesList;
import bot.users.TesterUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;
import org.jibble.pircbot.Colors;
import org.jibble.pircbot.PircBot;

/**
 * Clase encargada de gestionar la contestación de mensajes
 *
 * @author HumanoideFilms
 */
public class MessageAnswerer extends PircBot {

    //color del mensaje
    String color = Colors.PURPLE;
    //interfaz de control
    Gui gui;
    //el timer es para dar tiempo a los mensajes
    Timer timer;
    //inicia en 0
    int time = 0;
    //cola de mensajes
    Queue<Message> messages = new LinkedList<>();
    //cola de tiempos
    Queue<Double> times = new LinkedList<>();

    /**
     * constructor
     */
    public MessageAnswerer() {
        this.setName(variables.name);
        Users.initUsers();
        gui = new Gui(this);

        /*
        Timer qusado para dar tiempo a los mensajes y que el bot no conteste tan rapido
        para simular el tiempo de contestacion de un humano
        cada segundo es una interacion
         */
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //dead time se usa por si nadie responde, entonces el bot envia un mensaje a la sala
                    Time.deadTime++;
                    //si hay mensajes en la cola
                    if (messages.size() > 0) {
                        time++;
                        //si el tiempo sobrepasa el tiempo de la cola de mensajes, entonces se envia un mensaje
                        if (time >= times.peek()) {
                            Message m = messages.poll();
                            Msg.print("el objetivo es " + m.channel);
                            sendMessage(m.getChannel(), m.getText());
                            times.poll();
                            time = 0;
                        }
                    }
                    //si hay tiempo de inactividad, el bot envia un mensaje de la lista salasola
                    if (Time.deadTime > variables.deadTimeLimit) {
                        Time.deadTime = 0;
                        addMsg(color + Strings.chooseMessage(Listas.salaSola), variables.channel);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        });
        timer.start();

    }

    /**
     * acciones por si ocurre un mensaje privado
     *
     * @param sender
     * @param login
     * @param hostname
     * @param message
     */
    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        message = Colors.removeFormattingAndColors(message);
        Users.addUser(sender);
        if (message.length() < 1000) {
            if (message.contains("aprende:") || message.contains("aprendenn:")) {
                message = message.toLowerCase();
                boolean nn = message.contains("aprendenn:");
                message.replaceAll("r:", "");
                int indexTuser = Users.findTUser(sender);
                if (indexTuser != -1) {
                    TesterUser tuser = Users.tusers.get(indexTuser);
                    String question = message.replaceAll("aprendenn:", "");
                    question = question.replaceAll("aprende:", "");
                    question = formatText(question);
                    question = question.trim();
                    if (!nn) {
                        question = question + " name";
                    }
                    tuser.setLastQuestion(question);
                    /*Rule r=new Rule();
                r.addReact(question);*/
                    //RulesList.createRule(question, null);
                    this.sendMessage(sender, Colors.DARK_BLUE + "pregunta registrada, agregue respuestas con r: respuesta");
                } else {
                    this.sendMessage(sender, Colors.RED + "no tiene permisos para entrenar");
                }
            }
            if (message.contains("aprendeex:")) {
                message = message.toLowerCase();
                int indexTuser = Users.findTUser(sender);
                if (indexTuser != -1) {
                    TesterUser tuser = Users.tusers.get(indexTuser);
                    String question = message.replaceAll("aprendeex:", "");
                    question = formatText(question);
                    question = question.trim();
                    tuser.setLastQuestion("er:" + question);
                    /*Rule r=new Rule();
                    r.addReact(question);*/
                    //RulesList.createRule(question, null);
                    this.sendMessage(sender, Colors.DARK_BLUE + "ha registrado una expresion regular, agregue respuestas con r: respuesta");
                } else {
                    this.sendMessage(sender, Colors.RED + "no tiene permisos para entrenar");
                }
            }
            if (message.contains("r:")) {
                message = message.replaceAll("aprendenn:", "");
                message = message.replaceAll("aprende:", "");
                int indexTuser = Users.findTUser(sender);
                if (indexTuser != -1) {
                    TesterUser tuser = Users.tusers.get(indexTuser);
                    if (tuser.hasQuestion()) {
                        String ans = message.replaceAll("r:", "");
                        ans = ans.trim();
                        RulesList.createRule(tuser.getLastQuestion(), ans);
                        this.sendMessage(sender, Colors.DARK_BLUE + "tu respuesta ha sido registrada, puedes seguir mandando respuestas");
                    } else {
                        this.sendMessage(sender, Colors.RED + "no has hecho la pregunta para entrenar");
                    }
                } else {
                    this.sendMessage(sender, Colors.RED + "no tiene permisos para entrenar");
                }
            }
        } else {
            this.sendMessage(sender, Colors.RED + "texto muy largo");
        }
        /*
        Time.deadTime = 0;
        message = message + " " + variables.name;
        message = Colors.removeFormattingAndColors(message);
        message = message.toLowerCase();

        Msg.print("Mensaje recibido");
        if (isRead()) {
            saludar(sender, sender, message, true);
            send(sender, sender, message, true);
        }*/
    }

    /**
     * acciones que ocurren con un mensaje a la sala
     *
     * @param channel
     * @param sender
     * @param login
     * @param hostname
     * @param message
     */
    public void onMessage(String channel, String sender,
            String login, String hostname, String message) {
        Users.addUser(sender);
        Time.deadTime = 0;
        message = Colors.removeFormattingAndColors(message);
        message = message.toLowerCase();
        Msg.print("Mensaje recibido");
        if (isRead()) {
            saludar(channel, sender, message, false);
            send(channel, sender, message, false);
        }

    }

    /**
     * metodo para saludar a los nuevos usuarios que entran
     *
     * @param channel
     * @param sender
     * @param message
     * @param pv
     */
    public void saludar(String channel, String sender, String message, boolean pv) {
        if (!Users.haSaludado(sender)) {
            message = formatText(message);
            if (Strings.matcher(".*hola.*||.*ola.*||.*buenas.*", message)) {
                String dest = channel;
                if (pv) {
                    dest = sender;
                }
                addMsg(color + Strings.chooseMessage(Listas.saludos).replaceAll("sender", sender).replaceAll("channel", channel).replaceAll("name", variables.name), dest);
                Users.addSaludo(sender);
            }
        }
    }

    /**
     * metodo para enviar mensajes a la sala
     *
     * @param channel
     * @param sender
     * @param message
     * @param pv
     */
    public void send(String channel, String sender, String message, boolean pv) {
        //formatea el texto
        message = formatText(message);
        Msg.print("el mensaje es :" + message);
        String dest = channel;
        if (pv) {
            dest = sender;
        }
        for (Comands command : Listas.commandList) {
            if (Strings.matcher(command.expression, message)) {
                Msg.print("se cumple el comando : " + command.expression);
                addMsg(color + Strings.chooseMessage(command.responses).replaceAll("sender", sender).replaceAll("channel", channel), dest);
                return;
            } else {
                Rule bestRule = RulesList.selectBestRule(message.replaceAll(variables.name.toLowerCase(), "name"));
                if (bestRule != null) {
                    Msg.print("se cumplio la regla r" + bestRule.id);
                    Users.addRule(sender, bestRule.id);
                    addMsg(color + bestRule.getResponse().replaceAll("sender", sender).replaceAll("channel", channel), dest);
                    return;
                }
            }
        }
    }

    /**
     * metodo para limpiar el texto
     *
     * @param text
     * @return
     */
    public String formatText(String text) {
        String message;
        message = Normalizer.normalize(text, Normalizer.Form.NFD);
        message = message.replaceAll("[^\\p{ASCII}]", "");
        message = message.toLowerCase();
        message = message.replaceAll("[-+.^:,?¿!¡]", " ");
        message = message.trim();
        return message;
    }

    /**
     * metodo para saber si leyo o ignoro un mensaje el bot tiene una
     * probabilidad de leer
     *
     * @return
     */
    public boolean isRead() {
        double rand = Math.random();
        System.out.println("rnd es " + rand + " probabilidad de leer es de " + variables.readProbability);
        return rand <= variables.readProbability;
    }

    public void sendMessageRemote(String message) {
        sendMessage(variables.channel, Colors.MAGENTA + message);
    }

    /**
     * añade un mensaje a la cola de mensajes
     *
     * @param msg
     * @param channel
     */
    public void addMsg(String msg, String channel) {
        messages.add(new Message(msg, channel));
        times.add(Time.calculateTime(msg));
        Msg.print("tiempo de respuesta es " + times.peek());
    }

}
