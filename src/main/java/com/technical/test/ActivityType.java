package com.technical.test;

/**
 * @author Tom Longdon
 */
public enum ActivityType {
    CALL(1),
    EMAIL(2),
    MEETING(3);

    private int TYPE;

    ActivityType(int type){
        this.TYPE = type;
    }

    public int getType(){
        return TYPE;
    }

    @Override
    public String toString() {
        switch (TYPE){
            case 1: return "Call";
            case 2: return "Email";
            case 3: return "Meeting";
            default: return "Invalid Activity Type";
        }
    }
}
