package uz.pdp.g30springhibernate.repo;

public interface CRUDRepository<T, I> {
    T save(T t);
    T getById(I i);
    T update(T t);
    boolean deleteById(I i);
}
