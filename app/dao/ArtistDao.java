package dao;

import models.Artist;
import models.Song;
import models.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistDao extends GenericDao<Long, Artist> {

    private final String HQL_MINIFIED_ARTIST = "SELECT new Artist(a.id, a.name, a.msdId, a.lat, a.long_) " +
            "FROM Artist a";

    public ArtistDao() {
        super(Artist.class);
    }

    public List<Artist> artists() {
        return GenericDao.createQuery(HQL_MINIFIED_ARTIST).getResultList();
    }

    public List<Song> songs(long id) {
        return GenericDao.createQuery("SELECT a.songs FROM Artist a " +
                "WHERE a.id = :id").setParameter("id", id).getResultList();
    }

    public List<Tag> tags(long id) {
        return GenericDao.createQuery("SELECT a.tags FROM Artist a " +
                "WHERE a.id = :id").setParameter("id", id).getResultList();
    }

    public List<Artist> similarsFromArtist(long id) {
        return GenericDao.createQuery("SELECT new Artist(similar.id, similar.name, similar.msdId) FROM Artist a " +
                " LEFT JOIN a.similars as similar" +
                " WHERE a.id = :id").setParameter("id", id).getResultList();
    }

    public List<Artist[]> similars() {
        List<List<Object>> list = GenericDao.createQuery("SELECT new list(a.id, a.name, a.msdId, similar.id, similar.name, similar.msdId) FROM Artist a " +
                " JOIN a.similars as similar").getResultList();
        List<Artist[]> ret = new ArrayList<>(list.size());
        for(List o : list){
            Artist target = new Artist((Long) o.get(0), (String) o.get(1), (String) o.get(2));
            Artist similar = new Artist((Long) o.get(3), (String) o.get(4), (String) o.get(5));
            ret.add(new Artist[]{target, similar});
        }
        return ret;
    }

    @Override
    public Artist findById(Long id){
        return (Artist) GenericDao.createQuery(HQL_MINIFIED_ARTIST + " WHERE a.id=:id").
                setParameter("id", id).getSingleResult();
    }
}
