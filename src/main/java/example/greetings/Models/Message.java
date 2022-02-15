package example.greetings.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String tag;
    private String text;

    public  Message(){

    }

    public Message( String tag, String text) {
        this.tag = tag;
        this.text = text;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public String getText() {
        return text;
    }
}
