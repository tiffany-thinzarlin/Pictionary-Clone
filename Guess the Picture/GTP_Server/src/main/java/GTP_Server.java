import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GTP_Server extends Thread {
  protected static ServerSocket aServerSocket;
  protected static ArrayList<Socket> sockets;
  protected static ArrayList<String> players;
  protected static ArrayList<Integer> started;
  protected static ArrayList<Integer> score;
  protected static ArrayList<String> words;
  protected static int[] highScore;
  protected static ArrayList<String> highScorers;
  protected static int[] currIndex;
  protected static boolean gameStart;
  protected static ChangeWord cw;

  GTP_Server(int portNumber) {
    try {
      aServerSocket = new ServerSocket(portNumber);
      sockets = new ArrayList<Socket>();
      players = new ArrayList<String>();
      started = new ArrayList<Integer>();
      score = new ArrayList<Integer>();
      words = new ArrayList<String>();
      words.add("cat");
      words.add("dog");
      words.add("rabbit");
      words.add("lion");
      words.add("java");
      currIndex = new int[1];
      currIndex[0] = 0;
      gameStart = false;
      highScore = new int[1];
      highScore[0] = 0;
      highScorers = new ArrayList<String>();
      
      
    } catch (IOException e) {
      System.out.println("Error! Couldn't bind to port.");
    }
  }

  @Override
  public void run() {
    try {
      while (true) {
        Socket aSocket = aServerSocket.accept();
        Thread aThread = new Thread(new Runnable(){
          @Override
          public void run() {
            try {
              BufferedReader in = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
              String input;
              int index;
              String name = in.readLine();
              if (name != null){
                  sendMessage(name + " has entered the game.");
                  sendMessage("Enter START to start the game!");
                  players.add(name);
                  index = players.indexOf(name);
                  started.add(1);
                  score.add(0);
                  while ((input = in.readLine()) != null) {
                      
                      if (input.toLowerCase().equals("start") && (gameStart == false)){
                          started.set(index, 0);
                          sendMessage(name + " has started the game");
                          sendMessage(name + " has a score of 0.");
                          Integer sum = 0;
                          if (gameStart == false){
                              for (Integer currStart : started) {
                              sum += currStart;
                              //sendMessage(name + " "+ (Integer.toString(sum)));
                            }
                          }
                          
                          if (sum == 0){
                              gameStart = true;
                              for (String player : players){
                                  highScorers.add(player);
                                  highScore[0] = 0;
                              }
                              sendMessage("START GUESSING!");
                              sendMessage("You have 10 seconds to guess each picture!");
                              cw = new ChangeWord(currIndex, words.size(), highScore, highScorers);
                              cw.start();
                          }
                      }
                      else if (gameStart == true){
                          String currW = words.get(currIndex[0]);
                          input = input.toLowerCase();
                          if (input.equals(currW.toLowerCase())){
                              sendMessage(name + " guessed the word!");
                              Integer currScore = score.get(index);
                              currScore++;
                              score.set(index, currScore);
                              sendMessage(name + " has a score of "+ currScore);
                              if (currScore > highScore[0]){
                                  highScore[0] = currScore;
                                  highScorers.clear();
                                  highScorers.add(name);
                                  
                                  
                              }
                              else if (currScore == highScore[0]){
                                  highScorers.add(name);
                              }
                              
                          }
                          else{
                              sendMessage(name + ": "+input);
                          }
                      }
                      else{
                          sendMessage(name + ": "+input);
                      }
                    
                }

                  
              }

              
            }catch (IOException e) {
              e.printStackTrace();
            }

          }
        });

        aThread.start();
        sockets.add(aSocket);

      }

      
    } catch (IOException e) {
      e.printStackTrace();
    }

    
  }

  public static void sendMessage(String newMessage) {
    try {
      for (Socket socket : sockets) {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(newMessage);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private static final int PORT = 800;
  public static void main(String[] args) {
    GTP_Server server = new GTP_Server(PORT);
    server.start();
  }
}


class ChangeWord extends Thread{
    int[] currIndex;
    int size;
    int[] highScore;
    ArrayList<String> highScorers;
    ChangeWord(int[] index, int size, int[] highScore, ArrayList<String> highScorers){
        this.currIndex = index;
        this.size = size;
        this.highScore = highScore;
        this.highScorers = highScorers;

    }
    
    @Override
    public void run(){
        while (true){
            
            try{
                sleep(10000);

                
            }catch (InterruptedException ex) {
          System.out.println(ex);
      }
        if (currIndex[0] < size-1){
            currIndex[0] += 1;
            GTP_Server.sendMessage("Next Word!");
            }
        else{
            GTP_Server.sendMessage("Thank you for playing. The winner is: ");
            for (String highScorer: highScorers){
                GTP_Server.sendMessage(highScorer);
            }
            GTP_Server.sendMessage(" with a high Score of "+ highScore[0]+ " .");
            stop();
        }
        

        }
        
    }
}


