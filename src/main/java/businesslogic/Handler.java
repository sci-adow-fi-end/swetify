package businesslogic;

public abstract class Handler {

    private static NavigationManager navigationManager = new NavigationManager();

    protected abstract void pullData();
    protected abstract void render();
    protected abstract void handleInput();

    public void update(){
        pullData();
        render();
        handleInput();
    }
}
