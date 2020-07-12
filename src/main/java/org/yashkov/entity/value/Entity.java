package org.yashkov.entity.value;

public class Entity<K, R, W extends R> {
    public Entity(EntityRepository<K, Entity<K, R, W>> repo, K key)
    {
    }

    public K key()
    {
        // XXX
        return null;
    }

    public R get()
    {
        // XXX
        return null;
    }

    public W modify()
    {
        // XXX
        return null;
    }
}
