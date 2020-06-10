package it.polimi.ingsw.client.gui.game;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.VertexFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public abstract class Loader{
    /**
     * loadObj()
     *
     * Loads an OBJ file from disk and convert it to a mesh.
     *
     * @param path The file path to load the OBJ from.
     * @return The mesh of the selected file.
     **/
    public static MeshView loadObj(String path){
        if(path.startsWith("file:"))
            path = path.substring(6);
        TriangleMesh mesh = new TriangleMesh(VertexFormat.POINT_NORMAL_TEXCOORD);
        ArrayList<String> lines = readTextFile(path);
        for(int x = 0; x < lines.size(); x++){
            String line = lines.get(x);
            if(line != null){
                line = line.trim();
                if(line.length() < 2){
                    warning("loadObj", "Not enough data to parse line " + x);
                    continue;
                }
                switch(line.charAt(0)){
                    /* Comment */
                    case '#' :
                        /* Ignore comments */
                        break;
                    /* Polygonal face element */
                    case 'f' :
                        String[] faces = line.replace("f", "").trim().split(" ");
                        for(int y = 0; y < faces.length; y++){
                            String[] temp = faces[y].split("/");
                            /* NOTE: Java loads this in the wrong order. */
                            mesh.getFaces().addAll(Integer.parseInt(temp[0]) - 1);
                            mesh.getFaces().addAll(Integer.parseInt(temp[2]) - 1);
                            mesh.getFaces().addAll(Integer.parseInt(temp[1]) - 1);
                        }
                        break;
                    /* Group */
                    case 'g' :
                        warning("loadObj", "Cannot handle group on line " + x);
                        break;
                    /* Line element */
                    case 'l' :
                        warning("loadObj", "Cannot handle line on line " + x);
                        break;
                    /* Object */
                    case 'o' :
                        warning("loadObj", "Cannot handle object on line " + x);
                        break;
                    /* Smoothing */
                    case 's' :
                        warning("loadObj", "Cannot handle smoothing on line " + x);
                        break;
                    case 'v' :
                        switch(line.charAt(1)){
                            /* List of geometric vertices, with (x,y,z[,w]) coordinates */
                            case ' ' :
                                String[] verts = line.replace("v", "").trim().split(" ");
                                for(int y = 0; y < verts.length; y++){
                                    mesh.getPoints().addAll(Float.parseFloat(verts[y]));
                                }
                                break;
                            /* List of texture coordinates, in (u, v [,w]) coordinates */
                            case 't' :
                                String[] texts = line.replace("vt", "").trim().split(" ");
                                for(int y = 0; y < texts.length; y++){
                                    mesh.getTexCoords().addAll(Float.parseFloat(texts[y]));
                                }
                                break;
                            /* List of vertex normals in (x,y,z) form */
                            case 'n' :
                                String[] norms = line.replace("vn", "").trim().split(" ");
                                for(int y = 0; y < norms.length; y++){
                                    mesh.getNormals().addAll(Float.parseFloat(norms[y]));
                                }
                                break;
                            /* Parameter space vertices in ( u [,v] [,w] ) form */
                            case 'p' :
                                warning("loadObj", "Cannot handle vertices on line " + x);
                                break;
                            default :
                                warning("loadObj", "Bad vertex `" + line.charAt(1) + "`:" + x);
                                break;
                        }
                        break;
                    default :
                        warning("loadObj", "Bad command `" + line.charAt(0) + "`:" + x);
                        break;
                }
            }
        }
        return new MeshView(mesh);
    }

    public static ArrayList<String> readTextFile(String path){
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            ArrayList<String> lines = new ArrayList<String>();
            lines.add(br.readLine());
            while(lines.get(lines.size() - 1) != null){
                lines.add(br.readLine());
            }
            return lines;
        }catch(Exception e){
            error("readTextFile", "Exception thrown when reading `" + path + "`");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * warning()
     *
     * Display a warning message for a loading issue.
     *
     * @param mthd The method the failure happened in.
     * @param msg The message to be displayed as a result.
     **/
    private static void warning(String mthd, String msg){
        System.out.println(
                System.currentTimeMillis() +
                        " [??] Loader->" +
                        mthd +
                        "() " +
                        msg
        );
    }

    /**
     * error()
     *
     * Display an error message for a loading issue.
     *
     * @param mthd The method the failure happened in.
     * @param msg The message to be displayed as a result.
     **/
    private static void error(String mthd, String msg){
        System.out.println(
                System.currentTimeMillis() +
                        " [!!] Loader->" +
                        mthd +
                        "() " +
                        msg
        );
    }
}