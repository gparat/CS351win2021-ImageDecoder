/*
CS 351 Winter 2022 assignment 1
Written by Giovanni Parati
Reads dimensions and binary data from a file and translates it into an image
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class source {
    public static void main(String[] args) throws IOException {
        int rows;
        int cols;
        String next;
        int r;
        int g;
        int b;
        int x=-1;
        int y=0;
        Scanner reader=new Scanner(new File("question.dat"));
        //find the number of rows and columns
        rows=reader.nextInt();
        cols=reader.nextInt();
        BufferedImage canvas = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);

        while(reader.hasNext()){//loop through each item in the file
            next=reader.next();
            x++;//start at x=0, increment for each item in the file
            if(x>=cols){//if x has reached the image boundry, reset x to 0 and increment y
                x=0;
                if(y<rows){
                    y++;
                }
            }
            //extract integer representations of binary from the file
            r=Integer.parseInt(next.substring(0,next.indexOf(",")));
            next=next.substring(next.indexOf(",")+1);
            g=Integer.parseInt(next.substring(0,next.indexOf(",")));
            next=next.substring(next.indexOf(",")+1);
            b=Integer.parseInt(next);
            //convert binary to decimal
            r=getDecimal(r);
            g=getDecimal(g);
            b=getDecimal(b);
            //shift the r and g values, b doesnt need to shift
            r=r<<16;
            g=g<<8;
            //combine the r, g, and b values together
            int rgb=r+b+g;
            //System.out.println(x+" "+y+" "+Integer.toHexString(rgb));
            canvas.setRGB(x, y, rgb);//make a pixel at the the correct xy with the translated rgb value
        }
        ImageIO.write(canvas,"PNG",new File("output.png"));
}
    public static int getDecimal(int binary)//takes an integer representation of a binary number, returns a base 10 integer
    {
        int decimal = 0;
        int n = 0;
        while(true){//adds a power of 2 for each 1, based on the position of the 1
            if(binary == 0)
            {
                break;
            }
            else
            {
                int temp = binary%10;
                decimal += temp*Math.pow(2, n);
                binary = binary/10;
                n++;
            }
        }
        return decimal;
    }
}