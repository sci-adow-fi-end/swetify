package businesslogic;

import domainmodel.Track;

import java.util.Scanner;

public class PlaybackHandler extends Handler{

    private void renderChoices(){
        System.out.println("0: go back");
        System.out.println("1: pause/play");
        System.out.println("2: skip track");
    }

    private void printQueue(State state){
        System.out.println("Current track");
        System.out.println(state.getPlayingTrack().getTitle());
        System.out.println("\n");
        System.out.println("Next songs in queue:");
        for(Track t: state.getQueue()){
            System.out.println(t.getTitle());
        }
    }

    private void play (Track t) throws InterruptedException {
        
        int totalSteps = (int) t.getDuration().toSeconds();
        
        for (int i = 0; i <= totalSteps; i++) {
            Thread.sleep(100);
            
            printProgressBar(i, totalSteps);
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

            switch (navigationOption) {
                case 0:
                    navigationManager.previousState();
                    break;
                case 1:
                    try {
                        play(state.getPlayingTrack());
                    }catch (Exception ignored){}

                    break;
                case 2:
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_SUGGESTIONS);
                    break;

                default:
                    printError("inserted option not valid");
                    validNavigationOption=false;
            }
        }




        return state;
    }
}
