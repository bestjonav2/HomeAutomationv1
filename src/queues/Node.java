package queues;


public class Node {
    //variables
    private String dato;
    private Node next;
    //constructores
    public Node(){
        dato="";
        next=null;
    }
    public Node(String dato){
        this.dato = dato;
        next=null;
    }
    public Node(String dato,Node next){
        this.dato = dato;
        this.next = next;
    }
    //getters & setters
    public String getDato(){
        return dato;
    }
    public void setDato(String dato){
        this.dato=dato;
    }
    public Node getNext(){
        return next;
    }
    public void setNext(Node next){
        this.next=next;
    }
}

