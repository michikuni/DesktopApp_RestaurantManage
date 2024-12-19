/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 *
 * @author Admin
 */
public class DateUtil {


    public int prevousNumber;

    public int getPrevousNumber() {
        return prevousNumber;
    }

    public DateUtil() {
        this.prevousNumber = 0;
    }
    public LocalDate[] getDailyDate(){
        LocalDate currentDate = LocalDate.now();

        // Get the date of the first day of the previous week (Monday)
        LocalDate start = currentDate.minusDays(prevousNumber);

        // Get the date of the last day of the previous week (Sunday)
        LocalDate end = start.plusDays(1);
        LocalDate[] d = {start, end};
        System.out.println(start);
        System.out.println(end);
        return d;
    }
    public LocalDate[] getWeeklyDate() {
        LocalDate currentDate = LocalDate.now();

        // Get the date of the first day of the previous week (Monday)
        LocalDate start = currentDate.minusWeeks(this.prevousNumber).with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

        // Get the date of the last day of the previous week (Sunday)
        LocalDate end = currentDate.minusWeeks(this.prevousNumber).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate[] d = {start, end};
        System.out.println(start);
        System.out.println(end);
        return d;
    }
    public LocalDate[] getMonthlyDate(){
         LocalDate currentDate = LocalDate.now();

        // Get the date of the first day of the previous week (Monday)
        LocalDate start = currentDate.minusMonths(this.prevousNumber).with(TemporalAdjusters.firstDayOfMonth());

        // Get the date of the last day of the previous week (Sunday)
        LocalDate end = currentDate.minusMonths(this.prevousNumber).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate[] d = {start, end};
        System.out.println(start);
        System.out.println(end);
        return d;
    }
    public LocalDate[] getYearlyDate(){
         LocalDate currentDate = LocalDate.now();

        // Get the date of the first day of the previous week (Monday)
        LocalDate start = currentDate.minusYears(this.prevousNumber).with(TemporalAdjusters.firstDayOfYear());

        // Get the date of the last day of the previous week (Sunday)
        LocalDate end = currentDate.minusYears(this.prevousNumber).with(TemporalAdjusters.lastDayOfYear());
        LocalDate[] d = {start, end};
        System.out.println(start);
        System.out.println(end);
        return d;
    }
    
    public void getPrevious() {
        this.prevousNumber += 1;
    }
     public void getNext() {
        if(this.prevousNumber > 0){
            this.prevousNumber -= 1;
        }
        
    }
    public void getCurrent() {
       
            this.prevousNumber = 0;
      
        
    }

}
