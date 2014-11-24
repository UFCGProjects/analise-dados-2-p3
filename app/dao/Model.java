package dao;

import java.io.Serializable;

/**
 * Classe que adiciona métodos para salvar, remover e atualizar uma entidade.
 *
 * @param <I> Classe do id da entidade
 * @param <T> Classe da entitdade
 */
public abstract class Model<I, T> implements Serializable{

    private GenericDao<I, T> dao;
    private T entity;

    public abstract I getId();

    /**
     * Salva a entidade.
     */
    public void save() {
        dao.persist(entity);
        dao.flush();
    }

    /**
     * Remove a entidade.
     */
    public void remove() {
        dao.remove(entity);
        dao.flush();
    }

    /**
     * Atualiza a entidade.
     */
    public void update() {
        dao.merge(entity);
        dao.flush();
    }

    /**
     * Seta qual em que as operações de salvar, remover e atualizar serão efetuados
     *
     * @param entity Entidade em que serão executaras as operações.
     */
    protected void setEntity(T entity) {
        this.entity = entity;
        dao = new GenericDao<>((Class<T>) entity.getClass());
    }
}