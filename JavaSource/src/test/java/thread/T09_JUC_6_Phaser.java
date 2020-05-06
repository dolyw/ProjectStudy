package thread;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Phaser - 阶段器 - 用来解决控制多个线程分阶段共同完成任务的情景问题
 * 其作用相比CountDownLatch和CyclicBarrier更加灵活
 *
 * https://blog.csdn.net/u010739551/article/details/51083004
 *
 * @author wliduo[i@dolyw.com]
 * @date 2020/4/20 18:50
 */
public class T09_JUC_6_Phaser {

    static MarriagePhaser marriagePhaser = new MarriagePhaser();

    public static void main(String[] args) {

        marriagePhaser.bulkRegister(7);

        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }

        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }

    static class MarriagePhaser extends Phaser {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {

            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了！" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了！" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了！" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎新娘抱抱！" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }

        public void arrive() {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 到达现场！\n", name);
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 吃完!\n", name);
            marriagePhaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s 离开！\n", name);
            marriagePhaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if (name.equals("新郎") || name.equals("新娘")) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s 洞房！\n", name);
                marriagePhaser.arriveAndAwaitAdvance();
            } else {
                // 结束
                marriagePhaser.arriveAndDeregister();
                // marriagePhaser.register()
            }
        }

        @Override
        public void run() {
            arrive();

            eat();

            leave();

            hug();
        }
    }

}
