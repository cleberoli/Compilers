public class Label {
    
    private static int label;
    
    public Label() {
        label = 0;
    }
    
    public String newLabel() {
        return "R" + label++;
    }
    
}
