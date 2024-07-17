package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.State;
import domainmodel.entities.tracks.Track;

import java.util.Scanner;

public class PlaybackHandler extends Handler {

    public class PlaybackThread extends Thread {

        private boolean paused = true;
        private boolean skip = false;
        private businesslogic.utility.State state;
        int totalSteps;


        PlaybackThread(businesslogic.utility.State state){
            this.state=state;
            this.totalSteps = (int) state.getPlayingTrack().getDuration().toSeconds();
        }

        public void run() {
            int i=0;
            try {
                while (true) {

                    if (i <= totalSteps && !skip) {
                        if (!paused){
                            Thread.sleep(100);
                            printProgressBar(i, totalSteps);
                            i++;
                        }
                        else{
                            Thread.sleep(100);
                            printProgressBar(i, totalSteps);
                        }
                    }
                    else{
                        skip = false;
                        Thread.sleep(100);
                        i=0;
                        Track newTrack = state.getQueue().getNextSong();
                        state.setPlayingTrack(newTrack);
                        this.totalSteps = (int) state.getPlayingTrack().getDuration().toSeconds();

                    }
                }
            }catch (Exception ignored){}
        }

        public void playPause(){
            paused = !paused;
        }

        public void skip(){
            skip = true;
        }

        public boolean isPaused(){
            return paused;
        }

        public boolean isSkipped(){
            return skip;
        }
    }

    private void renderChoices(){
        System.out.println("0: go back");
        System.out.println("1: pause/play");
        System.out.println("2: skip track");
    }

    private void printQueue(State state){
        System.out.println("Current track");
        System.out.println(state.getPlayingTrack());
        System.out.println("\n");
        System.out.println("Next songs in queue:");
        for(Track t: state.getQueue()){
            System.out.println(t);
        }
    }

    public static void printProgressBar(int currentStep, int totalSteps) {
        int barLength = 50;
        int progress = (int) ((double) currentStep / totalSteps * barLength);

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < progress) {
                bar.append("=");
            } else {
                bar.append(" ");
            }
        }
        bar.append("]");

        int percent = (int) ((double) currentStep / totalSteps * 100);

        System.out.print("\r" + bar + " " + percent + "%");

        System.out.flush();

        if (currentStep == totalSteps) {
            System.out.println();
        }

    }

    public PlaybackThread createPlaybackQueue(State state){
        return new PlaybackThread(state);
    }

    @Override
    public State update(State state){
        clearScreen();


        renderChoices();
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner input = new Scanner(System.in);
        while (!validNavigationOption) {
            try {
                navigationOption = Integer.parseInt(input.nextLine());
                validNavigationOption = true;
            } catch (NumberFormatException ignored) {
                continue;
            }

            PlaybackThread pbt = new PlaybackThread(state);
            pbt.start();

            switch (navigationOption) {
                case 0:
                    pbt.interrupt();
                    try {
                        pbt.join();
                    }catch (Exception ignored){}
                    navigationManager.previousState();
                    break;
                case 1:
                    try {
                        pbt.playPause();
                    }catch (Exception ignored){}

                    break;
                case 2:
                    try {
                        pbt.skip();
                    }catch (Exception ignored){}

                    break;

                default:
                    printError("inserted option not valid");
                    validNavigationOption=false;
            }
        }

        return state;
    }
}
