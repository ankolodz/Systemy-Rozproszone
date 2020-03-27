package com.company;

import java.io.*;

public class Order implements Serializable {
    private OrderType type;
    private String stationName;
    private int id;
    private boolean finished;

    public Order(OrderType type, String stationName, int id) {
        this.type = type;
        this.stationName = stationName;
        this.finished = false;
        this.id = id;
    }
    public void work(){
        this.finished = true;
    }

    public byte[] toByte(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            return bos.toByteArray();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static Order createFromBythe(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Order order = (Order) in.readObject();
            return order;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    public String getTypeName(){
        return this.type.getName();
    }

    public String getStationName() {
        return stationName;
    }

    @Override
    public String toString() {
        return String.format(stationName + " " + id);
    }
}
