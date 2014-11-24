package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import dao.Model;

import javax.persistence.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity(name = "Song")
@JsonInclude(NON_DEFAULT)
public class Song extends Model<Long, Song> {

    @Id
    @SequenceGenerator(name = "SONG_SEQUENCE", sequenceName = "SONG_SEQUENCE", allocationSize = 1, initialValue = 0)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    private String msdId;
    private String title;
    private String echoNextId;
    private String release;
    private String musicBrainzId;
    private float duration;
    private int year;
    private float hotness;

    public Song() {
        super.setEntity(this);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMsdId() {
        return msdId;
    }

    public void setMsdId(String msdId) {
        this.msdId = msdId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEchoNextId() {
        return echoNextId;
    }

    public void setEchoNextId(String echoNextId) {
        this.echoNextId = echoNextId;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getMusicBrainzId() {
        return musicBrainzId;
    }

    public void setMusicBrainzId(String musicBrainzId) {
        this.musicBrainzId = musicBrainzId;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getHotness() {
        return hotness;
    }

    public void setHotness(float hotness) {
        this.hotness = hotness;
    }
}