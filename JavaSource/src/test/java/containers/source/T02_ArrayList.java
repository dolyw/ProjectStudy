package containers.source;


import java.util.Arrays;
import java.util.Collection;

/**
 * ArrayList源码解析
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/8/26 15:21
 */
public class T02_ArrayList<E> {

    /**
     * 默认数组大小
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 空数组，Shared empty array instance used for empty instances.
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 空数组，Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * 存储数据的数组，elementData.length为数组实际大小
     * 当新增元素size > elementData.length后就需要扩容数组，每次1.5倍
     */
    transient Object[] elementData;

    /**
     * 当前数组大小
     */
    private int size;

    /**
     * 最大数组容量
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 默认构造方法，可以设置数组初始化大小
     */
    public T02_ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    /**
     * 默认空构造方法，数组初始化空
     */
    public T02_ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造方法传集合，拿到传入的集合，转成数组，进行深拷贝，长度和传入集合长度一致
     */
    public T02_ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        // 传入的集合为空，也初始化空数组
        if ((size = elementData.length) != 0) {
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
        } else {
            // replace with empty array.
            this.elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 扩容前的判断
     * @param minCapacity
     */
    private void ensureExplicitCapacity(int minCapacity) {
        // 实际修改集合次数++ (在扩容的过程中没用，主要是用于迭代器中)
        /*modCount++;*/

        // 判断当前数组大小大于数组实际大小，则需要扩容数组
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    /**
     * 数组核心扩容方法
     *
     * @param minCapacity
     */
    private void grow(int minCapacity) {
        // 拿到当前数组实际大小
        int oldCapacity = elementData.length;
        // 默认为原来1.5倍 >> 右移 oldCapacity/2
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 判断新容量 - 小容量是否小于 0, 如果是第一次调用add方法必然小于
        if (newCapacity - minCapacity < 0)
            // 还是将小容量赋值给新容量
            newCapacity = minCapacity;
        // 判断新容量-大数组大小 是否>0，如果条件满足就计算出一个超大容量
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 调用数组工具类方法，创建一个新数组，将新数组的地址赋值给elementData
        // minCapacity is usually close to size, so this is a win:
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
     *
     * @param e
     * @return
     */
    public boolean add(E e) {
        // 扩容操作
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 赋值后size++
        elementData[size++] = e;
        return true;
    }

    private void ensureCapacityInternal(int minCapacity) {
        // 判断集合存数据的数组是否等于空容量的数组
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            // 通过小容量和默认容量求出较大值 (用于第一次扩容)
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        // 将if中计算出来的容量传递给下一个方法，继续校验
        ensureExplicitCapacity(minCapacity);
    }

    public void add(int index, E element) {
        // 数组越界判断
        rangeCheckForAdd(index);
        // 扩容判断
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        // 从index开始的数据全部后移，底层是native方法
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        // 当前坐标赋值
        elementData[index] = element;
        // size++
        size++;
    }

    private void rangeCheckForAdd(int index) {
        // 数组越界判断
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    /**
     * 获取指定下标的值
     *
     * @param index
     * @return
     */
    public E get(int index) {
        // 数组越界判断
        rangeCheck(index);
        // 获取
        /*return elementData(index);*/
        return null;
    }

    private void rangeCheck(int index) {
        // 数组越界判断
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * 更新指定坐标的值，返回值为旧值
     *
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element) {
        // 数组越界判断
        rangeCheck(index);
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
    public E remove(int index) {
        // 数组越界判断
        rangeCheck(index);

        /*modCount++;*/
        // 拿到移除的值
        /*E oldValue = elementData(index);*/
        E oldValue = null;
        // 计算集合需要移动元素个数
        int numMoved = size - index - 1;
        // 如果需要移动元素个数大于0
        if (numMoved > 0)
            // 删除的坐标后的数据全部往前移动一位，使用arrayCopy底层native方法进行拷贝
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        // 将源集合后一个元素置为null，尽早让垃圾回收机制对其进行回收
        elementData[--size] = null; // clear to let GC do its work
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
        if (o == null) {
            for (int index = 0; index < size; index++)
                // 元素等于null
                if (elementData[index] == null) {
                    // 如果相等，调用fastRemove方法快速删除
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                // 元素不等于null，用equals判断是否一样
                if (o.equals(elementData[index])) {
                    // 如果相等，调用fastRemove方法快速删除
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    private void fastRemove(int index) {
        /*modCount++;*/
        // 计算集合需要移动元素的个数
        int numMoved = size - index - 1;
        if (numMoved > 0)
            // 删除的坐标后的数据全部往前移动一位，使用arrayCopy底层native方法进行拷贝
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        // 将源集合后一个元素置为null，尽早让垃圾回收机制对其进行回收
        elementData[--size] = null; // clear to let GC do its work
    }

}
