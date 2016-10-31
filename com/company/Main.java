package com.company;

import java.util.*;


public class Main {

    public class Meeting {
        int id;
        int start;
        int end;

        public Meeting(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }
    }

    public class Schedule {
        int room;
        ArrayList<Meeting> meetings;
    }

    public static Boolean addToSchedule(List<Schedule> sched, Meeting mToAdd, int[] meetingRooms) {

        for (int roomId = 0; roomId < (meetingRooms.length - 1); roomId++) {
            if (roomId > (sched.size() - 1)) {
                createNewRoom(sched, roomId);
            }

            if (!checkOverlapForRoom(sched.get(roomId).meetings, mToAdd)) {
                sched.get(roomId).meetings.add(mToAdd);
                return true;
            }
        }

        return false;
    }

    public static Boolean checkOverlapForRoom(ArrayList<Meeting> mlist, Meeting m2) {

        for (Meeting m: mlist) {
            if (isOverlappingMeeting(m, m2)) {
                return true;
            }

        }
        return false;
    }

    public static Boolean isOverlappingMeeting(Meeting m1, Meeting m2) {

        if ( (m1.start >= m2.start && m1.start < m2.end) ||
                (m2.start >= m1.start && m2.start < m1.end) )
            return true;

        return false;
    }

    public static void createNewRoom(List<Schedule> list, int roomId) {
        Schedule s = new Main().new Schedule();
        s.room = roomId;
        if (s.meetings == null ) {
            s.meetings = new ArrayList<Meeting>();
        }
        list.add(roomId, s);

    }

    public static List<Schedule> getSchedule(ArrayList<Meeting> meetings) {
        List<Schedule> allMeetings = new ArrayList<>();

        //worst case we need as many meeting rooms as num of meetings if all meetings overlap
        int[] meetingRooms = new int[meetings.size()];

        for (int j=0; j<meetings.size() - 1; j++) {

            Meeting meetingToAdd = meetings.get(j);
            addToSchedule(allMeetings, meetingToAdd, meetingRooms);
        }

        return allMeetings;
    }

    public static void main(String[] args) {

        ArrayList<Meeting> meetings = new ArrayList<>();
        
        //generate a meeting list with random durations and start times
        for (int i =0; i<10; i++) {
            int rand = (int)(Math.random() * 10)  + 1;
            int duration = (int)(Math.random() * 2)  + 1;
            System.out.println("value of rand generated is "  + rand + " duration " + duration);
            meetings.add(new Main().new Meeting(i, rand, rand + duration));
        }
        //print the generated list just to see how it looks
        for (int i =0; i<meetings.size() - 1; i++) {
            System.out.println(meetings.get(i).id + " " + "start-end " + meetings.get(i).start + "-" +  meetings.get(i).end);
        }
        //create schedule now
        List<Schedule> s = getSchedule(meetings);
        
        //print the result
        for (Schedule s1: s) {
            System.out.println("Inside " +  s1.room);
            for (Meeting m: s1.meetings) {
                System.out.println(m.start + "-" + m.end);
            }
        }
        System.out.println("Finish");
    }
}
