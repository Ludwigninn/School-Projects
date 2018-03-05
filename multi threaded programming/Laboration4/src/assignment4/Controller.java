package assignment4;

/**
 *
 * Created by ludwig on 12/12/2016.
 */
public class Controller {
    private AdventurePoolBuffer adventurePoolBuff;
    private CommonPoolBuffer commonPoolBuffer;
    private EntranceWaitingAdventureBuffer entranceWaitingAdventureBuffer;
    private EntranceWaitingCommonBuffer entranceWaitingCommonBuffer;

    private ReceptionThread receptionThread;
    private CommonPoolThread commonPoolThread;
    private AdventurePoolThread adventurePoolThread;
    private CustomerExitThread customerExitThread;

    private GUISwimmingpool gui;

    public Controller() {
        initGui();
        initBuffers();
        initThreads();

    }

    public void initThreads() {
        receptionThread = new ReceptionThread(this, entranceWaitingAdventureBuffer, entranceWaitingCommonBuffer);
        commonPoolThread = new CommonPoolThread(this, commonPoolBuffer, adventurePoolBuff, entranceWaitingCommonBuffer);
        adventurePoolThread = new AdventurePoolThread(this, commonPoolBuffer, adventurePoolBuff, entranceWaitingAdventureBuffer);
        customerExitThread = new CustomerExitThread(this, commonPoolBuffer, adventurePoolBuff);

    }


    public void initBuffers() {
        adventurePoolBuff = new AdventurePoolBuffer();
        commonPoolBuffer = new CommonPoolBuffer();
        entranceWaitingAdventureBuffer = new EntranceWaitingAdventureBuffer();
        entranceWaitingCommonBuffer = new EntranceWaitingCommonBuffer();
    }

    public void initGui() {
        gui = new GUISwimmingpool(this);
        gui.Start();
    }

    public void start() {
        receptionThread.start();
        commonPoolThread.start();
        adventurePoolThread.start();
        customerExitThread.start();
    }

    public int getSizeEntranceWaitingCommon() {
        return entranceWaitingCommonBuffer.getSize();
    }

    public int getSizeEntranceWaitingAdventure() {
        return entranceWaitingAdventureBuffer.getSize();
    }

    public int getSizeAdventure() {
        return adventurePoolBuff.getSize();
    }

    public int getSizeCommon() {
        return commonPoolBuffer.getSize();
    }

    public int getSizeExitCommon() {
        return customerExitThread.getCountCommon();
    }

    public int getSizeExitAdventure() {
        return customerExitThread.getCountAdventure();
    }

    public void setCommenSizeLabel() {
        gui.setLblComNr(getSizeCommon());
    }

    public void setAdventureSizeLabel() {
        gui.setLblAdvNr(getSizeAdventure());
    }

    public void setEntranceWaitingCommonSizeLabel() {
        gui.setLblWadv(getSizeEntranceWaitingAdventure());
    }

    public void setEntranceWaitingAdventureSizeLabel() {
        gui.setLblWcom(getSizeEntranceWaitingCommon());
    }

    public void setExitCommonLabel() {

        gui.setLblCexit(getSizeExitAdventure());
    }

    public void setExitAdventureLabel() {
        gui.setLblAexit(getSizeExitCommon());
    }
    public void setLoopCond(boolean loopCond) {
       receptionThread.setLoopCond(loopCond);
    }

}
