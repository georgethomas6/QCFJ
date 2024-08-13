public class TurnInProgress {

    private String color;
    private String state;
    private int column;
    private int firstPlacement;
    private int secondPlacement;
    private boolean canModifyState;


    TurnInProgress(String color) {
        this.color = color;
        this.state = "certain";
        this.column = 3;
        this.canModifyState = true;
        this.firstPlacement = -1;
        this.secondPlacement = -1;
    }

    public String getColor() {
        return color;
    }

    public int getColumn() {
        return column;
    }

    public int getFirstPlacement() {
        return firstPlacement;
    }

    public int getSecondPlacement() {
        return secondPlacement;
    }

    public boolean getCanModifyState() {
        return canModifyState;
    }

    public void setColumn(int column){
        this.column = column;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setFirstPlacement(int column){
        this.firstPlacement = column;
    }

    public void setSecondPlacement(int column) {
        this.secondPlacement = column;
    }

    public void setColor(String color){
        this.color = color;
    }

    public void setCanModifyState(boolean truthValue){
        this.canModifyState = truthValue;
    }

    public void incrementPosition(){
        this.column++;
        if (column > 6){
            this.column = 0;
        }
    }

    public void decrementPosition(){
        this.column--;
        if (column < 0){
            column = 6;
        }
    }

    public void changeState(){
        switch (this.state) {
            case "certain" -> this.state = "horizontal";
            case "horizontal" -> this.state = "vertical";
            case "vertical" -> this.state = "certain";
        }
    }
}
