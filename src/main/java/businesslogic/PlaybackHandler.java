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

    private void play(){

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
                    navigationManager.switchToController(NavigationManager.HandlerId.VIEW_PLAYLIST);
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
