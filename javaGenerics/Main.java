package generics;

import java.io.*;

public class Main {
    public static void main(String[] args) throws InterruptedException{
        Circular<String> bufor = new Circular<String>(20);
        new Thread(new Producer(bufor)).start();
        Thread.sleep(20000);
        new Thread(new Consumer(bufor)).start();
        Thread.sleep(10000);
        new Thread(new Access(bufor)).start();
    }
    // watek zapisujacy dane do bufora cyklicznego
    static class Producer extends Thread{
        private Circular buf;

        public Producer(Circular buf){
            this.buf = buf;
        }

        String[] string = {"Koloseum", "Ekwipunek", "Hazard", "Danina"};

        public void run(){
            try{
                for(int i=0; i<20; i++){
                    buf.put(string[i%4]);
                    System.out.println("Producer: " + string[i%4]);
                    Thread.sleep(1000);
                }
            }catch(InterruptedException error){
                error.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread{
        private Circular buf;

        public Consumer(Circular buf){
            this.buf = buf;
        }

        public void run(){
            // ObjectOutputStream wspiera serializacje
                try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("file.ser"))) {
                    for(Object s : buf) {
                        System.out.println(s);
                    }
                    buf.writeObject(output);
                }catch (IOException e) {
                    e.getMessage();
                }
        }
    }
    static class Access extends Thread{
        private Circular buf;

        public Access(Circular buf){
            this.buf = buf;
        }

        public void run(){

                try(ObjectInputStream input = new ObjectInputStream(new FileInputStream("file.ser"))){
                    //Circular<String> read = buf;
                    buf.readObject(input);
                    System.out.println(buf.toString());
                }catch(IOException e){
                    e.getMessage();
                }
        }
    }
}
