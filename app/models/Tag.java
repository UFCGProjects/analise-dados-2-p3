package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dao.Model;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static javax.persistence.EnumType.STRING;

@Entity(name = "Tag")
@JsonInclude(NON_DEFAULT)
public class Tag extends Model<Long, Tag> {

    @Id
    @SequenceGenerator(name = "TAG_SEQUENCE", sequenceName = "TAG_SEQUENCE", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    private String tagName;

    @Enumerated(STRING)
    private Type type;

    public Tag(){
        super.setEntity(this);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        ECHO_NEST, MUSIC_BRAINZ
    }
}
