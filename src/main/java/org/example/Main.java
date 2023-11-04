package org.example;

import com.raylib.Jaylib;
import com.raylib.Jaylib.Vector2;
import com.raylib.Raylib.Color;
import com.raylib.Raylib;
import com.raylib.Jaylib.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Vector;

import static com.raylib.Jaylib.*;

public class Main {

    static final public int MAX_BUILDINGS = 100;
    public static void main(String[] args) {

        Random rand = new Random();
        final int screenWidth = 800;
        final int screenHeight = 450;

        InitWindow(screenWidth, screenHeight, "2D camera");

        Jaylib.Rectangle player = new Jaylib.Rectangle(400, 270, 40, 40);
        ArrayList<Jaylib.Rectangle> buildings = new ArrayList<Jaylib.Rectangle>(100);
        ArrayList<Jaylib.Color> buildingColours = new ArrayList<Jaylib.Color>(100);

        int spacing = 0;

        //Sets a random height and coulour for the background buildings
        for (int i = 0; i < 100; i++) {
            buildings.add(new Jaylib.Rectangle(GetRandomValue(50,200),GetRandomValue(100, 800), screenHeight - 130.0f,-600.0f + spacing));

            spacing += (int)buildings.get(i).width();

            buildingColours.add(new Jaylib.Color(GetRandomValue(200,240), GetRandomValue(200, 240), GetRandomValue(200, 250), 255));
        }

        //Sets up the camera object and associates it with a target and offset function
        Raylib.Camera2D camera = new Raylib.Camera2D(0);
        camera.target(new Vector2(player.x() + 20.0f, player.y() + 20.0f));
        camera.offset(new Vector2(screenWidth/2.0f, screenHeight/2.0f));
        camera.rotation(0.0f);
        camera.zoom(1.0f);

        SetTargetFPS(60);

            while (!WindowShouldClose()){

                //Player movement
                if (IsKeyDown(KEY_RIGHT)){
                    player.x(+2);
                }
                else if (IsKeyDown(KEY_LEFT)){
                    player.x(-2);
                }

                //Camera follows player
                Camera2D target = camera.target(new Vector2(player.x() + 20, player.y() + 20));

                //Camera rotation
                if (IsKeyDown(KEY_A)){
                    camera.rotation(-1);
                }
                else if (IsKeyDown(KEY_D)){
                    camera.rotation(+1);
                }

                //Limit camera rotation
                if (camera.rotation() >40){
                    camera.rotation(40);
                }
                else if (camera.rotation()< -40){
                    camera.rotation(-40);
                }

                //Camera zoom control
                camera.zoom(+(float)GetMouseWheelMove()* 0.05f);

                if (camera.zoom() > 3.0f){
                    camera.zoom(3.0f);
                }

                //Camera reset
                if (IsKeyPressed(KEY_R)){
                    camera.zoom(1.0f);
                    camera.rotation(0.0f);
                }

                //Draw
                BeginDrawing();

                    ClearBackground(RAYWHITE);

                    BeginMode2D(camera);

                    DrawRectangle(-6000, 320, 13000, 8000, DARKGRAY);

                for (int i = 0; i < 100; i++) {
                    DrawRectangleRec(buildings.get(i), buildingColours.get(i));
                }

                    DrawRectangleRec(player, RED);
                    DrawLine((int)camera.target().x(), -screenHeight*10, (int)camera.target().x(), screenHeight*10, GREEN);
                    DrawLine(-screenWidth*10, (int)camera.target().y(), screenWidth*10, (int)camera.target().y(), GREEN);

                EndMode2D();

                DrawText("SCREEN AREA", 640, 10, 20, RED);

                DrawRectangle(0,0,screenWidth,5, RED);
                DrawRectangle(0, 5, 5, screenHeight - 10, RED);
                DrawRectangle(screenWidth - 5, 5, 5, screenHeight - 10, RED);
                DrawRectangle(0,screenHeight - 5, screenWidth, 5, RED);

                DrawRectangle(10,10, 250, 113, Fade(SKYBLUE, 0.5f));
                DrawRectangleLines(10, 10,250,113,BLUE);

                DrawText("Free 2d camera controls:", 20, 20, 10, BLACK);
                DrawText("- Right/Left to move Offset", 40, 40, 10, DARKGRAY);
                DrawText("- Mouse Wheel to Zoom in-out", 40, 60, 10, DARKGRAY);
                DrawText("- A / D to Rotate", 40, 80, 10, DARKGRAY);
                DrawText("- R to reset Zoom and Rotation", 40, 100, 10, DARKGRAY);

                EndDrawing();
        }

            CloseWindow();
    }
}