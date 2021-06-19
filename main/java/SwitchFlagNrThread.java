import java.util.Set;

public class SwitchFlagNrThread implements Runnable {
    private Set<SweeperButton> buttons;
    private boolean doSwitch;
    private boolean setNrs;

    public SwitchFlagNrThread(Set<SweeperButton> buttons) {
        this.buttons = buttons;
        this.doSwitch = true;
        this.setNrs = false;
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (this.doSwitch) {
            this.setNrs = !setNrs;
            for (SweeperButton b : this.buttons) {
                if (this.setNrs) b.emitReveal(true);
                else b.rightClick(true);
            }

            try {
                Thread.sleep(790);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.doSwitch = false;
    }
}
