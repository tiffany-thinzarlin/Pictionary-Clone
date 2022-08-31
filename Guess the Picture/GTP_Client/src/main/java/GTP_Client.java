/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tiffanyyan, barryzhang
 */
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class GTP_Client {
    static int port = 800;
    static JFrame jf;
    static JPanel jp1;   
    static JPanel jp2;
    static JPanel jp3;
    static JButton jb;
    static JPanel imagePanel;
    static BufferedImage image;
    static JLabel iconLabel;
    static ArrayList<BufferedImage> images;
    static int[] currImageIndex;
    static int imageCount;
    //static JPanel main;
    
    static JTextField jt1;
    static JTextField jt2; 
    static JTextField jt3; 
    static JTextArea jt4; 
    
    static JLabel jl1;
    static JLabel jl2; 
    static JLabel jl3; 
    static JLabel jl;   
    
    
    static JScrollPane scroll;
    static String address = "";
    static String name = null;   
    static String curr = "";
    static String currentWords = "";
    
    static ImageChange ichange; 
    

    
    static Boolean gameStarted = false;
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
    Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
    BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
    outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
    return outputImage;
}
    
    public static void main(String[] args) {
        
        currImageIndex = new int[1];
        currImageIndex[0] = 0;
        imageCount = 5;
        images = new ArrayList<BufferedImage>();
        jf = new JFrame("GUESS THE WORD!");
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp1.setBackground(Color.WHITE);
        jp2.setBackground(Color.WHITE);
        jp3.setBackground(Color.WHITE);
        jp3.setBorder(BorderFactory.createLineBorder (Color.black));
        jl = new JLabel("");
        jl.setSize(400, 400);
        ichange = new ImageChange();
        
        jl1 = new JLabel("Server IP Address to join");
        jl2 = new JLabel("Username");
        jl3 = new JLabel("Enter message:");   
        jb = new JButton("SEND");   
        jt1 = new JTextField("");
        jt1.setColumns(10);
        jt4 = new JTextArea("Chat");
        jt4.setColumns(50);
        jt4.setAlignmentY(400);
        jt4.setEditable(false);
 
        jt2 = new JTextField("");
        jt2.setToolTipText("Enter your username");
        jt2.setColumns(10);   
        jt3 = new JTextField("");
        jt3.setToolTipText("Enter your message");
        jt3.setColumns(20);
        jt3.setEditable(true);
        

        jp1.add(jl1, BorderLayout.PAGE_START);
        jp1.add(jt1, BorderLayout.PAGE_START);
        jp1.add(jl2, BorderLayout.PAGE_START);
        jp1.add(jt2, BorderLayout.PAGE_END);
        jp3.add(jl, BorderLayout.PAGE_START);
        
        imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setLayout(new FlowLayout());
        image = null;
        
        try{
            URL menu = new URL("https://lh3.googleusercontent.com/a_mgQffxZ9C77Rl9D036Nuc234XKf1ON34gB0mrjE-AjE1HVls3o6mEaCBg0vpqZ-m4");
            URL cat = new URL("https://i.natgeofe.com/n/46b07b5e-1264-42e1-ae4b-8a021226e2d0/domestic-cat_thumb_square.jpg");
            URL dog = new URL("https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/322868_1100-800x825.jpg");
            URL rabbit = new URL("https://www.thesprucepets.com/thmb/BKNJkoyr-qyvfaYVRVCuEHNmOXU=/1155x1155/smart/filters:no_upscale()/Stocksy_txp14acff329Kw100_Medium_1360769-5aec7baefa6bcc00373c6cb7.jpg");
            URL lion = new URL("https://cdn2.momjunction.com/wp-content/uploads/2016/03/Fascinating-Facts-About-Lions-For-Kids-1-624x702.jpg");
            URL java = new URL("https://d3njjcbhbojbot.cloudfront.net/api/utilities/v1/imageproxy/https://coursera-course-photos.s3.amazonaws.com/0a/8cd7f1b14344618b75142593bc7af8/JavaCupLogo800x800.png?auto=format%2Ccompress&dpr=1");
            image = ImageIO.read(menu);
            image = resizeImage(image,300,300);
            images.add(image);
            image = ImageIO.read(cat);
            image = resizeImage(image,300,300);
            images.add(image);
            image = ImageIO.read(dog);
            image = resizeImage(image,300,300);
            images.add(image);
            image = ImageIO.read(rabbit);
            image = resizeImage(image,300,300);
            images.add(image);
            image = ImageIO.read(lion);
            image = resizeImage(image,300,300);
            images.add(image);
            image = ImageIO.read(java);
            image = resizeImage(image,300,300);
            images.add(image);
            
                    
        } catch (IOException e){
            e.printStackTrace();
        }


        ichange.setPic();
        ichange.setPreferredSize(new Dimension(400,400));

        
       
        
        
        jp3.add(jt4, BorderLayout.CENTER);
        jp2.add(jl3, BorderLayout.LINE_START);
        jp2.add(jt3, BorderLayout.CENTER);
        jp2.add(jb, BorderLayout.EAST);

        scroll = new JScrollPane (jt4);
        jf.setSize(800,800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(jp1, BorderLayout.PAGE_START);
        jf.add(ichange, BorderLayout.LINE_START);
        jf.add(jp3, BorderLayout.LINE_END);
        jf.add(jp2, BorderLayout.SOUTH);
        jf.add(scroll);
        jf.setVisible(true);    

 
        try{
            Socket aSocket = new Socket(address, port);
            PrintWriter cout = new PrintWriter(aSocket.getOutputStream(), true);
            jt2.addActionListener(new Name(cout));
            jb.addActionListener(new Message(cout));
            jt3.addActionListener(new Message(cout));
            new Read(aSocket).start();
           
        }
        catch(IOException e){
            System.out.println("Connection has failed");
        }   
    }   
    static class Name implements ActionListener{
        PrintWriter out;
        Name(PrintWriter newOut){out = newOut;}
        @Override
        public void actionPerformed(ActionEvent ae){
            JTextField jt = (JTextField)ae.getSource();
            name = jt.getText();
            out.println(name);
            jt.setEditable(false);   
        }
    }
    static class Message implements ActionListener{
        PrintWriter out;
        Message(PrintWriter newOut){out = newOut;}
        @Override
        public void actionPerformed(ActionEvent ae){
            String line = jt3.getText();
            out.println(line);
            jt3.setText("");   
        }   
    }   
    static class Read extends Thread{
        Socket s;
        Read(Socket newS){
            s = newS;
        }

        synchronized public void run(){
            Scanner sin;
            curr = jt4.getText();
            try {
                sin = new Scanner(s.getInputStream());
                while(!s.isClosed()){   
                    while(sin.hasNext()){
                        currentWords = sin.nextLine();
                        curr =curr+"\n"+currentWords;
                        jt4.setText(curr);
                        if (currentWords.equals("START GUESSING!")){
                            gameStarted = true;
                            currImageIndex[0] = currImageIndex[0]+1;
                            ichange.setPic();
                            

                        }
                        if (currentWords.equals("Next Word!")){
                            if (currImageIndex[0] < imageCount){
                                currImageIndex[0] = currImageIndex[0]+1;
                                ichange.setPic();
                            }
                            
                        }
                    }
                }   
            } catch (IOException ex) {
                System.out.println("EXIT");
            }
        }
        
        
    }
    static class ImageChange extends JPanel{
        
        ImageChange(){
            super();
        }
        
        public void setPic(){
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(images.get(currImageIndex[0]), 50, 50, null);            
        }
    }

    
    
    
    
}