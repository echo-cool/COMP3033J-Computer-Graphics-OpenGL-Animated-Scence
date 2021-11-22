package base.objects3D;

import base.GraphicsObjects.Utils;
import org.lwjgl.opengl.GL11;


public class Grid {

    static float[] black = {0.0f, 0.0f, 0.0f, 1.0f};
    static float[] white = {1.0f, 1.0f, 1.0f, 1.0f};


    public Grid() {

    }

    // Do not touch this class, I have implmented to help you in your Assignment 3 and project
    public void DrawGrid() {
        int nGridlines = 50;

        int x, z;
        // edges don't reflect
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(black));
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, Utils.ConvertForGL(black)); // but they do emit
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glPushMatrix();
        for (x = -nGridlines; x <= nGridlines; x++) { // for each x
            if ((x % 50 > 0)) GL11.glLineWidth((float) 0.1);

            else GL11.glLineWidth((float) 0.1);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3i(x, 0, -nGridlines);
            GL11.glVertex3i(x, 0, +nGridlines);
            GL11.glEnd();
        } // for each x


        for (z = -nGridlines; z <= nGridlines; z++) { // for each y
            if ((z % 50 > 0)) GL11.glLineWidth((float) 0.1);
            else GL11.glLineWidth((float) 0.1);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3i(-nGridlines, 0, z);
            GL11.glVertex3i(+nGridlines, 0, z);
            GL11.glEnd();
        } // for each y
        GL11.glLineWidth((float) 1.0);
        GL11.glPopMatrix();
        // stop emitting, otherwise other objects will emit the same colour
        GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_EMISSION, Utils.ConvertForGL(black)); // but they do emit


    }


}










 
	/*
	 On the grid 
                                                 =           $     ~     D     I     D     ~     $           =                                                 .
                   ~           =           +     N     ?     M     I     M     I     M     I     N     ?     D     +           =           ~                   .
                   $     D     8     Z     M     7     D     O     O     N     I     N     Z     O     D     7     M     $     8     8     $                   .
                         :           7     ~           Z     +     N     8     I     8     N     ?     Z           =     $           :                         .
                              ,                        ~     .     D     Z     I     O     D     .     ~                        ,                              .
                  N     M     M     ,     :                        $     7     I     $     Z                        ,     .     M     M     D                  .
                              .     N     D                        ?     I     I     I     ?                        D     D     ,     .                        .
                 ~     .            =     I     +     :     .      :     +     I     +     :      .     :     +     I     +            .     :                 .
                 Z     N                        N     Z     +            ~     I     ~            +     Z     N                        N     Z                 .
                             N     I     .      +     8     8            ,     I     ,            8     D     ?      .     I     N                             .
                N     +      :     O     8            +     D                  I                  N     +            O     O     :      +     N                .
                      $                  7                  7                  I                  7                  $                  $                      .
               ~            Z                  +            :     .            I            .     :            +                  Z            ~               .
               $            ?     O            M     .            =            I            =            .     N            O     ?            Z               .
                     7            I            I     7            7            I            7            7     I            I            7                     .
               ......?.....I............~............M............8............7............O............M............~............I.....I......               .
              D            $            N            7            M            I            M            $            N            Z            D              .
                    Z                   ?     =      ,            8            I            8            ,      ~     ?                   Z     .              .
             :      =     ~      8            D                   7            I            $                   D            8      ~     =      :             .
             I            Z      =     :      I            +      =     ,      I      ,     =      +            I      :     =      Z            I             .
                   M                   D                   Z      .     ~      I      ~     ,      Z                   D                   M                   .
            D                   +      I                   M            +      I      +            M                   I      +                   D            .
                         :      D            =      +      Z            I      I      I            Z      =      =            D      ,                         .
           :      :      M      .            D      8      =            $      I      7            =      8      D            .      M      ,      :           .
           Z      D      .            $      $      8                   O      I      Z                   8      $      $            .      D      Z           .
                               $      O             =            ,      8      I      8      ,            =      .      O      $                               .
                 =      D      $                                 +      N      I      N      +                          .      $      D      ~                 .
          D      O      ~                                 ,      $      M      I      M      7      ,                                 =      O      8          .
                              ~                    :      7      D      D      I      D      D      7      :                    :                              .
         +++++++O++++++D++++++M++++++I++++++$++++++8++++++N++++++M++++++8++++++Z++++++8++++++M++++++N++++++8++++++$++++++I++++++M++++++D++++++O+++++++         .
         I      +      =      :      N      M      N      8      Z      $      I      $      O      8      N      M      N      ~      =      ?      I         .
                                     ?      ?      ?      ?      I      I      I      I      I      I      I      ?      ?                                     .
        D      O      $                                   ,      ~      +      I      +      ~      ,                                   $      O      D        .
               ~      I      ~      .                                   ~      I      =      .                                   ~      7      =      .        .
       :                     M      O      +                            :      I      :                            +      Z      M                     :       .
       Z                     :      $      N      ?                     .      I      .                     ?      N      $      :                     O       .
              N      =                     7      N      =                     I                     =      N      7                     =      D              .
              ,      8      I                     Z      Z                     I                     Z      Z                     I      8      ,              .
      M      :              7      $              ,      N      ~              I              ~      M      ,              $      7              :      M      .
             8      =              O      +              $      I              I              ?      $              +      O              ~      8             .
     +              D      ,              N              ~      Z              I              Z      =              N      .       ,      D              +     .
     7      =              N              I      =              N              I              N              =      7              N              =      7     .
            Z      .       =      ?              8              D              I              N              O              ?      =       .      Z            .
    8              M              N              8              Z              I              Z              8              N              M              8    .
    .      ?       ,      +       ~      ~       =              ?              I              ?              +       ~      ~       =      :       ?      .    .
   ,       7              D              D                      :              I              :                      8              N              7       ,   .
   O                      .              $                                     I                                     $              ,                      O   .
                  D              .                      =                      I                      ~                                     D                  .
          8       ~              O                      $                      I                      $                      Z              ~       8          .
  M       :              7       $              ,       M                      I                      M       ,              $       7              :       M  .
                 Z       Z                      7       O                      I                      O       7                      Z       $                 .
 =???????M???????Z??????????????????????Z???????M???????$??????????????????????O??????????????????????$???????M???????Z??????????????????????Z???????M???????= .
 7       .                      I       M       7                      .       I                      .       7       M       I                      .       7 .
        .       ?       8       D       ?       .                      ,       I       ,                      ,       I       D       8       ?       .        .
8       N       Z       ?       :                                      ~       I       ~                                      :       ?       Z       M       8.
,                                                      .       ~       +       I       +       :       .                                                      

The grid, a digital frontier

I tried to picture clusters of information as they moved through the computer.

 What do they look like? Ships, motorcycles. With the circuits like freeways, I kept dreaming of a world, I thought I'd never see.

And then, one day, I got in
	*/
	 