/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.logic.AutomaticLearn;
import bot.logic.Msg;
import bot.users.Users;
import bot.logic.Rule;
import bot.logic.RulesList;
import bot.users.TesterUser;
import bot.users.User;
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
                            //Msg.print("el objetivo es " + m.channel);
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
            if (message.toLowerCase().contains("aprende:") || message.toLowerCase().contains("aprendenn:")) {
                message = message.toLowerCase();
                boolean nn = message.contains("aprendenn:");
                message.replace("r:", "");
                int indexTuser = Users.findTUser(sender);
                if (indexTuser == -1) {
                    Users.addTesterUser(sender);
                    indexTuser = Users.findTUser(sender);
                }
                if (indexTuser != -1) {
                    TesterUser tuser = Users.tusers.get(indexTuser);
                    String question = message.replace("aprendenn:", "");
                    question = question.replace("aprende:", "");
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
                    this.sendMessage(sender, Colors.RED + "no tiene permisos para entrenar, dile a L_Clocker que te agregue en la lista de usuarios permitidos");
                }
            }
            if (message.toLowerCase().contains("aprendeex:")) {
                message = message.toLowerCase();
                int indexTuser = Users.findTUser(sender);
                if (indexTuser == -1) {
                    Users.addTesterUser(sender);
                    indexTuser = Users.findTUser(sender);
                }
                if (indexTuser != -1) {
                    TesterUser tuser = Users.tusers.get(indexTuser);
                    String question = message.replace("aprendeex:", "");
                    question = formatText(question);
                    question = question.trim();
                    tuser.setLastQuestion("er:" + question);
                    /*Rule r=new Rule();
                    r.addReact(question);*/
                    //RulesList.createRule(question, null);
                    this.sendMessage(sender, Colors.DARK_BLUE + "ha registrado una expresion regular, agregue respuestas con r: respuesta");
                } else {
                    this.sendMessage(sender, Colors.RED + "no tiene permisos para entrenar, dile a L_Clocker que te agregue en la lista de usuarios permitidos");
                }
            }
            if (message.toLowerCase().contains("r:")) {
                message = message.replace("aprendenn:", "");
                message = message.replace("aprende:", "");
                int indexTuser = Users.findTUser(sender);
                if (indexTuser == -1) {
                    Users.addTesterUser(sender);
                    indexTuser = Users.findTUser(sender);
                }
                if (indexTuser != -1) {
                    TesterUser tuser = Users.tusers.get(indexTuser);
                    if (tuser.hasQuestion()) {
                        String ans = message.replace("r:", "");
                        ans = ans.replace("R:", "");
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
        fillUsers();
        //Users.addUser(sender);
        Time.deadTime = 0;
        message = Colors.removeFormattingAndColors(message);
        message = message.toLowerCase();
        AutomaticLearn.learn(message, sender);
        double probTalkingMe = talkingWithMeProb(message, sender);
        if (probTalkingMe > 0.5) {
            if (!message.contains(variables.name.toLowerCase())) {
                message = message + " name";
            }
        }
        if (!message.contains(variables.name.toLowerCase()) && probTalkingMe < -9) {
            message = "12345";
        }
        // Msg.print("Mensaje recibido");
        if (isRead()) {
            saludar(channel, sender, message, false);
            send(channel, sender, message, false);

        }
    }

    public void fillUsers() {
        org.jibble.pircbot.User[] users = this.getUsers(variables.channel);
        for (org.jibble.pircbot.User user : users) {
            Users.addUser(user.getNick());
        }
    }

    public double talkingWithMeProb(String message, String sender) {
        double prob = 1;
        message = message.replace(variables.name.toLowerCase(), "name");
        int userID = Users.findUser(sender);
        if (message.contains("name")) {
            Users.users.get(userID).isTalkingWithMe(1);
            prob = 1;
        } else {
            double probTalk = Users.users.get(userID).isTalkingWithMe - 0.1;
            if (probTalk < 0) {
                probTalk = 0;
            }
            prob = probTalk;
            Users.users.get(userID).isTalkingWithMe(probTalk);
            for (User u : Users.users) {
                if (message.contains(u.getName().toLowerCase())) {
                    Users.users.get(userID).isTalkingWithMe(-10);
                    prob = -10;
                    return prob;
                }
            }
        }
        return prob;
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
                addMsg(color + Strings.chooseMessage(Listas.saludos).replace("sender", sender).replace("channel", channel).replace("name", variables.name), dest);
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
        // Msg.print("el mensaje es :" + message);
        String dest = channel;
        if (pv) {
            dest = sender;
        }
        //boolean exp = false;
        for (Comands command : Listas.commandList) {
            if (Strings.matcher(command.expression, message)) {
                Msg.print("se cumple el comando :                  " + command.expression);
                addMsg(color + Strings.chooseMessage(command.responses).replace("sender", sender).replace("channel", channel), dest);
                // exp = true;
            }
        }

        Rule bestRule = RulesList.selectBestRule(message.replace(variables.name.toLowerCase(), "name"));
        if (bestRule != null) {
            Msg.print("se cumplio la regla r" + bestRule.id);
            Users.addRule(sender, bestRule.id);
            String text = executeCommand(bestRule.getResponse().replace("sender", sender).replace("channel", channel), sender);
            String lines[] = splitText(text);
            if (text.length() > 1) {
                for (String line : lines) {
                    addMsg(color + line, dest);
                }
            }

        }

    }

    public String executeCommand(String text, String sender) {
        String string = text;
        if (text.contains(Comands.commands.get(0))) {
            string = string.replace(Comands.commands.get(0), "");
            prob0(sender);
        }
        return string;
    }

    public void prob0(String sender) {
        Users.getUser(sender).isTalkingWithMe = -10;
    }

    public String[] splitText(String text) {
        String lines[];
        lines = text.split(Comands.commands.get(1));
        return lines;
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
        // System.out.println("rnd es " + rand + " probabilidad de leer es de " + variables.readProbability);
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
        //Msg.print("tiempo de respuesta es " + times.peek());
    }

}
