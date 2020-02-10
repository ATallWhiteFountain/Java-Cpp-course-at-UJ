package generics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Circular <E extends Serializable> implements Serializable, BlockingQueue<E> {
    // Collection<E>, Iterable<E>, Queue<E>

    private transient E[] buf;
    private int head; // miejsce gdzie przechowywana jest ostatnia zapisana wartosc
    private int tail; // miejsce gdzie przechowywana jest najdawniej zapisana wartosc - pierwsze do odczytu
    // zapis danych - inkrementacja head
    // odczyt danych - inkrementacja tail
    private int elemCount; // zapis aktualnej ilosci elementow w buforze
    // zapis - inkrementacja elemCount
    // odczyt - dekrementacja

    @SuppressWarnings("unchecked")
    public Circular(int size){
        buf = (E[]) new Serializable[size];
        tail = 0;
        head = 0;
        elemCount = 0;
    }

    @Override
    // dodaje element do bufora jesli to mozliwe, w przeciwnym wypadku wyrzuca exception
    synchronized public boolean add(E e) throws IllegalStateException{
        if(!offer(e)) throw new IllegalStateException("Bufor przepelniony");
        return true;
    }

    @Override
    // dodaje element do bufora jesli nie jest on przepelniony, zwraca true lub false zaleznie od pomyslnosci operacji
    synchronized public boolean offer(E e) throws NullPointerException{
        if(e == null) throw new NullPointerException(); // BlockingQueue nie przyjmuje elementow null
        if(elemCount == buf.length) return false; // false gdy bufor przepelniony
        buf[tail] = e;
        // gdy tail nie przekracza buf.length, % nic nie zmienia, gdy tail==buf.length % daje wynik 0
        tail = ++tail % buf.length;
        elemCount++;
        // notify all dla wszystkich metod oczekujacych na poll()
        if(elemCount > 0){
            notifyAll();
        }
        return true;
    }

    @Override
    // dodaje element do bufora jesli nie jest on przepelniony, jesli jest, czeka wyznaczona ilosc czasu
    synchronized public boolean offer(E e, long time, TimeUnit timeUnit) throws InterruptedException {
        if(remainingCapacity() == 0) wait(time);
        return offer(e);
    }

    @Override
    // Umieszcza element w buforze, czeka dopoki brak w nim miejsca
    synchronized public void put(E e) throws InterruptedException {
        while(remainingCapacity() == 0) wait();
        offer(e);
    }

    @Override
    // Zwracac i usuwa head element
    synchronized public E remove() {
        E retrieved = poll();
        if(retrieved == null) throw new IllegalStateException("Bufor jest pusty");
        return retrieved;
    }

    @Override
    // Zwraca i usuwa element bedacy head w buforze, zwraca element badz null w zaleznosci od sukcesu operaccji
    synchronized public E poll() {
        if(elemCount == 0) return null;
        E retrieved = buf[head];
        buf[head] = null; // zapobieganie wyciekowi pamieci
        // gdy head nie przekracza buf.length, % nic nie zmienia, gdy head==buf.length % daje wynik 0
        head = ++head % buf.length;
        elemCount--;
        // notify all dla wszystkich metod oczekujacych na offer()
        if(elemCount < buf.length) notifyAll();
        return retrieved;
    }

    @Override
    // Zwraca i usuwa element bedacy head w buforze, zwraca poll() jesli bufor nie jest pusty, gdy jest czeka zadany czas
    synchronized public E poll(long time, TimeUnit timeUnit) throws InterruptedException {
        if(elemCount == 0) wait(time);
        return poll();
    }

    @Override
    // Zwraca i usuwa head element, jesli zajdzie taka potrzeba to czeka dopoki nie uda sie tego zrobic
    synchronized public E take() throws InterruptedException {
        while(elemCount == 0) wait();
        return poll();
    }
    @Override
    // Zwraca aktualna pojemnosc bufora (zalezy od elemCount)
    synchronized public int remainingCapacity() {
        return buf.length - elemCount;
    }
    // Podczas serializacji/deserializacji bufora cyklicznego należy zapisać/odczytać jedynie używane, a nie wszystkie elementy buf
    // Metody writeObject i readObject pozwalaja na zmodyfikowanie domyslnego zachowania mechanizmu serializacji

    // Metoda writeObject(java.io.ObjectOutputStream stream), którą zaimplementujesz jest automatycznie wywoływana w momencie
    // zapisywania obiektu do strumienia, czyli w trakcie wywołania metody ObjectOutputStream.writeObject().
    // argument - strumien do ktorego pisze
    public void writeObject(ObjectOutputStream out) throws IOException {

        // ArrayList.toArray(T[]) method returns an array containing all of the elements in this list in proper sequence
        if(elemCount != buf.length){
            E[] newBuf = toArray(buf);
            out.writeObject(newBuf);
        }else{
            out.writeObject(buf);
        }
    }

    // Metoda readObject(java.io.ObjectInputStream stream), którą zaimplementujesz jest automatycznie wywoływana w momencie
    // odczytywania obiektu ze strumienia, czyli w trakcie wywołania metody ObjectInputStream.readObject()
    // argument - strumien z ktorego czytam
    public void readObject(ObjectInputStream in) throws IOException{
        try{
            E[] newBuf = (E[])in.readObject();
            head = 0;
            for(int i=0; i<newBuf.length; i++){
                buf[i] = newBuf[i];
            }
            elemCount = newBuf.length;
        }catch(ClassNotFoundException ex){
            ex.getMessage();
        }
    }

    @Override
    // kopiowanie wartosci z bufora do statycznej tablicy
    synchronized public <T> T[] toArray(T[] ts) {
        // jesli otrzymana tablica jest zbyt malego rozmiaru
        // korzystamy z newInstance() z pakietu reflect
        if(ts.length < elemCount) ts = (T[])Array.newInstance(ts.getClass().getComponentType(), elemCount);

        // przerywamy kopiowanie obiektow w momencie osiagniecia elemCount tzn. przepisania wszystkich elementow z bufora
        for(int i=head, j=0; j<elemCount; j++, i=(++i % buf.length) ){
            ts[j] = (T)buf[i];
        }

        // jesli przekazana tablica jest zbyt dluga w porownaniu z buforem, puste miejsca sa wypelniane null
        if(ts.length > elemCount) ts[elemCount] = null;

        return ts;
    }
    // metoda tworzy pole String w ktorym zapisane sa informacje o kazdym elemencie bufora
    // mozna tez uzyc StringBuildera
    synchronized public String toString(){
        String string = "";

        for(int i=0; i<buf.length; i++){
            string += Integer.toString(i) + " " + Integer.toString(elemCount - i);
            if(buf[i]==null) string += " empty\n";
            else{
                string += " " + buf[i].getClass().getSimpleName();
                string += " " + buf[i].toString() + "\n";
            }
        }

        return string;
    }

    @Override
    // zwraca obiekt CircularIterator ktory jest iteratorem
    public Iterator<E> iterator() {
        return new CircularIterator();
    }

    private class CircularIterator implements Iterator<E>{

        private int nextIndex;
        private E nextElement;

        private CircularIterator(){
            // gdy bufor niepusty identyfikujemy nextElement = head
            if(elemCount > 0){
                nextIndex = head;
                nextElement = buf[head];
            }else{
                nextIndex = -1;
            }
        }

        @Override
        // zwraca true jesli mozna dalej iterowac
        public boolean hasNext() {
            return (nextIndex >= 0);
        }

        @Override
        // zwraca nastepny element
        public E next() {
            // Blok synchronizowany powoduje zajęcie monitora dla danego obiektu i zwolnienie monitora kiedy opuszczamy blok.
            // Monitor może być zajęty przez jeden wątek na raz. Metoda synchronizowana oznacza że na czas jej wykonania
            // zajęty zostaje monitor obiektu
            synchronized(Circular.this) {
                if (!hasNext()) throw new NoSuchElementException();

                // uzyskiwanie elementu
                E e = nextElement;

                // ustawianie nastepnego elementu oraz jego indeksu
                nextIndex = ++nextIndex % buf.length;
                if (nextIndex != tail) {
                    nextElement = buf[nextIndex];
                    if (nextElement == null) nextIndex = -1;
                } else {
                    nextIndex = -1;
                    nextElement = null;
                }
                return e;
            }
        }
    }
    //##############################################################
    // Ponizsze metody musza sie znajdowac w klasie ze wzgledu na implementowanie BlockingQueue<E>, ale sam nie mam dla
    // nich zastosowania

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public E element() {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public E peek() {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<E> spliterator() {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public Stream<E> stream() {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public Stream<E> parallelStream() {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();
        //return 0;
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
        //return false;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
        //return new Object[0];
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        throw new UnsupportedOperationException();
        //return null;
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        throw new UnsupportedOperationException();
        //return 0;
    }

    @Override
    public int drainTo(Collection<? super E> collection, int i) {
        throw new UnsupportedOperationException();
        //return 0;
    }
}
