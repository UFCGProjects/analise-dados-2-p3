package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import dao.ArtistDao;
import dao.GenericDao;
import dao.Model;
import models.Artist;
import models.Song;
import models.Tag;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.similars;

import java.io.*;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;
import static play.mvc.BodyParser.Json;

public class Application extends Controller {

    private static GenericDao<Long, Song> songDao = new GenericDao<>(Song.class);
    private static ArtistDao artistDao = new ArtistDao();
    private static GenericDao<Long, Tag> tagDao = new GenericDao<>(Tag.class);

    public static Result index() {
        return ok(index.render());
    }

    public static Result similarsShow() {
        return ok(similars.render());
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result newSong() {
        return persistFromRequestBody(Song.class, "song");
    }

    @Transactional(readOnly = true)
    public static Result songs() {
        return ok(toJson(songDao.findAll()));
    }

    @Transactional(readOnly = true)
    public static Result song(long id) {
        return findById(songDao, id, "song");
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result setSong(long id) {
        return updateFromRequestBody(id, Song.class, "song");
    }

    public static Result deleteSong(long id) {
        return removeFromId(id, songDao, "song");
    }

    @Transactional
    public static Result newArtist() {
        return persistFromRequestBody(Artist.class, "artist");
    }

    @Transactional(readOnly = true)
    public static Result artists() {
        return ok(toJson(artistDao.artists()));
    }

    @Transactional(readOnly = true)
    public static Result artist(long id) {
        return findById(artistDao, id, "artist");
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result setArtist(long id) {
        return updateFromRequestBody(id, Artist.class, "artist");
    }

    @Transactional
    public static Result deleteArtist(long id) {
        return removeFromId(id, artistDao, "artist");
    }

    @Transactional
    public static Result similars() {
        return ok(toJson(artistDao.similars()));
    }

    @Transactional
    public static Result newTag() {
        return persistFromRequestBody(Tag.class, "tag");
    }

    @Transactional(readOnly = true)
    public static Result tags() {
        return ok(toJson(tagDao.findAll()));
    }

    @Transactional(readOnly = true)
    public static Result tag(long id) {
        return findById(tagDao, id, "tag");
    }

    @Transactional
    @BodyParser.Of(Json.class)
    public static Result setTag(long id) {
        return updateFromRequestBody(id, Tag.class, "tag");
    }

    @Transactional
    public static Result deleteTag(long id) {
        return removeFromId(id, tagDao, "Tag");
    }

    private static <I, T> Result findById(GenericDao<I, T> dao, I id, String entityName) {
        Object a = dao.findById(id);
        if (a != null) {
            return ok(toJson(dao.findById(id)));
        } else {
            return notFound("There is no " + entityName + " in data base with id: " + id);
        }
    }

    private static <I, T extends Model<I, T>> Result updateFromRequestBody(I id, Class<T> EntityClass, String entityName) {
        T jsonObject = null;
        try {
            JsonNode json = request().body().asJson();
            jsonObject = fromJson(json, EntityClass);

            if (!jsonObject.getId().equals(id)) {
                return badRequest("You cannot set the id from a " + entityName);
            }
            jsonObject.update();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof UnrecognizedPropertyException) {
                return badRequest("The json is not a valid json from a " + entityName + ".\n" +
                        e.getMessage());
            } else {
                return internalServerError("An error happened when trying to update the " + entityName);
            }
        }
        return ok("/" + entityName + "/" + jsonObject.getId());
    }

    private static <I, T extends Model<I, T>> Result removeFromId(I id, GenericDao<I, T> dao, String entityName) {
        try {
            dao.removeById(id);
            dao.flush();
        } catch (IllegalArgumentException e) {
            return notFound("There is no " + entityName + " with the id " + id);
        }
        return noContent();
    }

    private static <I, T extends Model<I, T>> Result persistFromRequestBody(Class<T> EntityClass, String entityName) {
        T jsonObject = null;
        try {
            JsonNode json = request().body().asJson();
            jsonObject = fromJson(json, EntityClass);

            jsonObject.save();
        } catch (RuntimeException e) {
            if (e.getCause() instanceof UnrecognizedPropertyException) {
                return badRequest("The json is not a valid json from a " + entityName + ".\n" +
                        e.getMessage());
            } else {
                return internalServerError("An error happened when trying to persist the " + entityName);
            }
        }
        return created("/" + entityName + "/" + jsonObject.getId());
    }

    @Transactional(readOnly = true)
    public static Result songFromArtist(long id) {
        return ok(toJson(artistDao.songs(id)));
    }

    @Transactional(readOnly = true)
    public static Result tagFromArtist(long id) {
        return ok(toJson(artistDao.tags(id)));
    }

    @Transactional(readOnly = true)
    public static Result similarFromArtist(long id) {
        return ok(toJson(artistDao.similarsFromArtist(id)));
    }
}