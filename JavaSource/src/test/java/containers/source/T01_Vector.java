package containers.source;

import java.util.Arrays;
import java.util.Collection;

/**
 * Vector源码解析
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/26 15:21
 */
public class T01_Vector<E> {

    /**
     * 存储数据的数组，大小构造方法可以赋值，initialCapacity数组初始化大小默认为10
     */
    protected Object[] elementData;

    /**
     * 当前数组中的有效数据大小(就是size方法返回值)，比如数组是10的空间，但是只有8个有效数据，后面的数据都为null
     */
    protected int elementCount;

    /**
     * 数组增量因子，如果大于0就是当前的数量，小于等于0默认为原来的两倍，构造方法可以赋值
     */
    protected int capacityIncrement;

    /**
     * 最大数组容量
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 默认构造方法，可以设置数组初始化大小和增量因子
     */
    public T01_Vector(int initialCapacity, int capacityIncrement) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        this.elementData = new Object[initialCapacity];
        this.capacityIncrement = capacityIncrement;
    }

    /**
     * 默认构造方法，默认数组大小10，设置增量因子为0，默认两倍扩容
     */
    public T01_Vector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    /**
     * 默认构造方法，默认数组大小10，设置增量因子为0，默认两倍扩容
     */
    public T01_Vector() {
        this(10);
    }

    /**
     * 构造方法传集合，拿到传入的集合，转成数组，进行深拷贝，长度和传入集合长度一致
     */
    public T01_Vector(Collection<? extends E> c) {
        elementData = c.toArray();
        elementCount = elementData.length;
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
            elementData = Arrays.copyOf(elementData, elementCount, Object[].class);
    }


    /**
     * 扩容前的判断
     *
     * @param minCapacity
     */
    private void ensureCapacityHelper(int minCapacity) {
        // 判断当前最小有效数据大于数组长度，则需要扩容数组
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * 数组核心扩容方法
     *
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        // 拿到当前数组大小
        int oldCapacity = elementData.length;
        // 判断数组增量因子是否大于0，大于0，扩容量为增量因子的值，不大于0默认为原来两倍
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                capacityIncrement : oldCapacity);
        // 扩容后的大小比当前最小数据量还小的话，直接用最小数据量
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        // 扩容后的大小比最大数组容量还大的话，就使用返回一个更大的数Integer.MAX_VALUE
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 拿到最后数据扩容大小，创建新数组，再把原来数据复制到新数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    /**
     * 添加方法，返回的永远的true
     */
    public synchronized boolean add(E e) {
        /*modCount++;*/
        // 先进行扩容判断
        ensureCapacityHelper(elementCount + 1);
        // 然后将新加的数据放入有效数据的下一位
        elementData[elementCount++] = e;
        return true;
    }

    /**
     * 指定位置添加(插入)，没有返回值
     *
     * @param index
     * @param element
     */
    public void add(int index, E element) {
        insertElementAt(element, index);
    }

    public synchronized void insertElementAt(E obj, int index) {
        /*modCount++;*/
        // 必须是有效数据内的插入才行，不然会出现越界异常
        if (index > elementCount) {
            throw new ArrayIndexOutOfBoundsException(index
                    + " > " + elementCount);
        }
        // 进行扩容判断
        ensureCapacityHelper(elementCount + 1);
        // 从index开始的数据全部后移，底层是native方法
        System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
        // 当前坐标赋值
        elementData[index] = obj;
        // elementCount++
        elementCount++;
    }

    /**
     * 获取指定下标的值
     *
     * @param index
     * @return
     */
    public synchronized E get(int index) {
        // 必须是有效数据内的才行，不然会出现越界异常
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        // 获取
        /*return elementData(index);*/
        return null;
    }

    /**
     * 更新指定坐标的值，返回值为旧值
     *
     * @param index
     * @param element
     * @return
     */
    public synchronized Object set(int index, E element) {
        // 必须是有效数据内的才行，不然会出现越界异常
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        // 拿到旧值
        /*E oldValue = elementData(index);*/
        E oldValue = null;
        // 更新新值
        elementData[index] = element;
        // 返回值为旧值
        return oldValue;
    }

    /**
     * 移除指定下标，返回被删除的元素
     *
     * @param index
     * @return
     */
    public synchronized E remove(int index) {
        /*modCount++;*/
        // 必须是有效数据内的才行，不然会出现越界异常
        if (index >= elementCount)
            throw new ArrayIndexOutOfBoundsException(index);
        // 拿到移除的值
        /*E oldValue = elementData(index);*/
        E oldValue = null;
        // 计算集合需要移动元素个数
        int numMoved = elementCount - index - 1;
        // 如果需要移动元素个数大于0
        if (numMoved > 0)
            // 删除的坐标后的数据全部往前移动一位，使用arrayCopy底层native方法进行拷贝
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        // 将源集合后一个元素置为null，尽早让垃圾回收机制对其进行回收
        elementData[--elementCount] = null; // Let gc do its work
        // 返回被删除的元素
        return oldValue;
    }

    /**
     * 移除当前元素，返回成功失败
     *
     * @param o
     * @return
     */
    public boolean remove(Object o) {
        return removeElement(o);
    }

    public synchronized boolean removeElement(Object obj) {
        /*modCount++;*/
        // 查找当前元素存在的左边
        int i = indexOf(obj);
        // 存在去移除，返回true，不存在返回false
        if (i >= 0) {
            removeElementAt(i);
            return true;
        }
        return false;
    }

    public int indexOf(Object o) {
        // 从0开始找
        return indexOf(o, 0);
    }

    public synchronized int indexOf(Object o, int index) {
        // 找到返回坐标，没找到为-1
        if (o == null) {
            for (int i = index ; i < elementCount ; i++)
                // 元素等于null
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = index ; i < elementCount ; i++)
                // 元素不等于null，用equals判断是否一样
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    public synchronized void removeElementAt(int index) {
        /*modCount++;*/
        // 必须是有效数据内的才行，不然会出现越界异常
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        // 计算集合需要移动元素个数
        int j = elementCount - index - 1;
        // 如果需要移动元素个数大于0
        if (j > 0) {
            // 删除的坐标后的数据全部往前移动一位，使用arrayCopy底层native方法进行拷贝
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        // 将源集合后一个元素置为null，尽早让垃圾回收机制对其进行回收
        elementCount--;
        elementData[elementCount] = null; /* to let gc do its work */
    }

}
