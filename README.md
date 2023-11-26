# Computer Graphics Project

## Introduction

This is a full 3D computer graphics project. Using lwjgl.

* Project Overview: This project builds a OpenGL application based on Lightweight Java. Game Library. I builtan articulated human mode by simulating the movement of bones and ioints. The ca culation was done by using lococoordinate systems. The third person perspective was achieved by using homogeneous matriz transformationand lighting was achieved by using the phone liohting model. This project is also highly optimized by utilizingDisplayList and programmable shaders on GPU cores.
* Projective Rendering: OpenGL uses a projective rendering pipeline to render the scene. In my proiect, I definerall the vertex and edges using Displaylist and then send them to VRAM for primitive assembly. A view frustumwas also defined in my application for clipping the geometries.
* GLSL Shaders: OpenGL provides the abilities to run on programmable shaders on GPU cores. I achieve(gradient ramp in my scene by using GLSL.
* Physics Simulation: In my scene, a collision detector was implemented, the mechanism is to view every objectsas a sphere and calculate the center distance to determine whether two objects colides

![https://v1.yuyangwang.org/assets/images/OpenGL%20Project%20-%20An%20Animated%20Scene%20(Java).png](https://v1.yuyangwang.org/assets/images/OpenGL%20Project%20-%20An%20Animated%20Scene%20(Java).png)

## Run

I have tested on my computer(macOS) with GL version "OpenGL version is 2.1 ATI-4.6.20" and Windows10 computer with GL version 4.6

Both of these two version can run this project perfectly.

To start the program, run Main.java

## Performance

I have tested this program on RTX 3060 it can run at 60 fps with VSync on.

But in the video, I am using a Mac computer so the FPS might not very high...

## Control

WASD to move.
Space to jump.
F to change the perspective.
Shift to go up
Enter to go down

That's it.

## Video

The video has been accelerated to meet the requirement of 3 minutes.... So I suggest you run this project on your computer to see the scence and anmiations....
