package model;

//Node class
public class MotorNode{
    MotorStock data;
    MotorNode left;
    MotorNode right;

    MotorNode(MotorStock data) {
        this.data = data;
        left = null;
        right = null;
    }

}
