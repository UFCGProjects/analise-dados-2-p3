package dao;

import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.List;

/**
 * Serviços de um Data Access Object (DAO)
 *
 * @param <I> Classe do id da entidade
 * @param <T> Classe da entitdade
 */
public class GenericDao<I, T> {

    private Class<T> clazz;

    public GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Persiste uma entidade no Banco de Dados.
     */
    public boolean persist(T entity) {
        JPA.em().persist(entity);
        return true;
    }

    /**
     * Espelha o estado do DAO com o banco de Dados, deve ser feito após um
     * persist, ou merge.
     */
    public void flush() {
        JPA.em().flush();
    }

    /**
     * Atualiza a informação da entidade do código com a do banco de dados.
     */
    public void merge(T entity) {
        JPA.em().merge(entity);
    }

    /**
     * Procura uma certa {@code clazz} pelo seu {@code id}.
     */
    public T findById(I id) {
        return JPA.em().find(clazz, id);
    }

    /**
     * Procura todos os objetos de uma certa classe pelo seu {@code className}
     * descrito na Entidade.
     */
    public List<T> findAll() {
        String hql = "FROM " + clazz.getSimpleName();
        Query hqlQuery = JPA.em().createQuery(hql);
        return hqlQuery.getResultList();
    }

    /**
     * Deleta do banco de dados uma {@code classe} referenciada pelo seu
     * {@code id}.
     */
    public void removeById(I id) {
        JPA.em().remove(findById(id));
    }

    /**
     * Remove o respectivo {@code objeto} do banco de dados.
     */
    public void remove(T entity) {
        JPA.em().remove(entity);
    }

    /**
     * Procura um objeto pelo seu {@code attributeName}.
     */
    public List<T> findByAttributeName(String attributeName, String attributeValue) {
        String hql = "FROM " + clazz.getSimpleName() + " c" + " WHERE c." + attributeName
                + " = '" + attributeValue + "'";
        Query hqlQuery = JPA.em().createQuery(hql);
        return hqlQuery.getResultList();
    }

    /**
     * Cria uma Query HQL
     */
    protected static Query createQuery(String query) {
        return JPA.em().createQuery(query);
    }
}
