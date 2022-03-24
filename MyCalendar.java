/**
 * Name: Anh Bach
 * Email: tbach@ucsd.edu
 * Sources used: None
 * 
 * This file contain MyCalendar class uses tree map
 * to implement the BST 
 */

 /**
  * This class contains methods to book an event 
  from start to end time 
  */
  public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;
    
    /**
     * Constructor to initializes the calendar object
     */
    public MyCalendar() {
        calendar = new MyTreeMap<>();
    }
    
    /**
     * Add the event to calender
     * @param start event's start time 
     * @param end event's end time
     * @return true if event is added, 
     * false otherwise
     */
    public boolean book(int start, int end) {
        // Exception
        if (start < 0 || start >= end) {
            throw new IllegalArgumentException();
        }

        // greater or equal to given key
        Integer afterStart = calendar.ceilingKey(start);
        // less than or equal to given key
        Integer beforeStart = calendar.floorKey(start);

        // 0 <= start <= i < end
        // If there is no book in calender 
        if ((Integer)beforeStart == null &&
            (Integer)afterStart == null) {
            calendar.put(start, end);
            return true;
        }

        // when there is event that already started
        // Event before started time 
        if (beforeStart != null) {
            Integer lastEnd = this.calendar.get(beforeStart);
            // Start time is within last book
            if (start < lastEnd) {
                return false;
            }
            // Start and end time are within last book 
            if (beforeStart < start && lastEnd > end) {
                return false;
            }
            if (start < beforeStart && end > lastEnd) {
                return false;
            }
        }

        // Event after started time
        if (afterStart != null) {
            Integer nextEnd = this.calendar.get(afterStart);
            // End time is within last book
            if (afterStart < end ) {
                return false;
            }
            // Start and end time are within last book 
            if (afterStart < start && nextEnd > end) {
                return false;
            }
            if (start < afterStart && end > nextEnd) {
                return false;
            }
        }

        // Add event to calender
        calendar.put(start, end);
        return true;
    }

    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}