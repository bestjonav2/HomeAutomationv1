package queues;


public class Queue {
    private Node ftNode;
    private Node ltNode;
    public Queue(){
        ftNode=null;
        ltNode=null;
    }
    public Queue(Node ftNode){
        this.ftNode=ftNode;
        this.ltNode=ftNode;
    }
    //Queue = First in First out
    //Agregar nodo al final(First in)
    public void addNode(Node newNode){
        if (ftNode==null) {
            this.ftNode=newNode;
            this.ltNode=newNode;
        }else{
            ltNode.setNext(newNode);
            ltNode=newNode;
        }
    }
    //Leer nodo de el inicio(First out)
    public String readNode()throws Exception{
        String val="";
        if(ftNode!=null){
            val = ftNode.getDato();
            ftNode=ftNode.getNext();
            if (ftNode==null){
                ltNode=null;
            }
        }else{
            throw new Exception("El queue esta vacio.");
        }
        return val;
    }
    public int length(){
        int c=0;
        Node temp = ftNode;
        while(temp!=null){
            c++;
            temp=temp.getNext();
        }
        return c;
        
    }
}
