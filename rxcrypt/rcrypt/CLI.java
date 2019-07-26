/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rcrypt;

import java.util.Scanner;

/**
 *
 * @author miket
 */
class CLI {
    private Scanner sc = new Scanner(System.in);
    public void initServer(){
        System.out.println("Please enter ip address to bind to: ");
        String ipAddr = sc.nextLine();
        
    }
}
