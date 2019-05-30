package com.sinensia.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Decoradores en forma de anotación
@WebServlet( asyncSupported = true, urlPatterns = "/api/productos" )
public class ProductoRestController extends HttpServlet        
{
    
    private ServicioProductoSingleton sps;
    
    @Override
    public void init() {
        ServicioProductoSingleton sps = ServicioProductoSingleton.getInstancia();
    }
    
    @Override
    protected void doPut(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter escritorRespuesta =  response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        // 
        BufferedReader bufRead = request.getReader();
        
        StringBuilder textoJson = new StringBuilder();
        for (String lineaJson = bufRead.readLine(); 
                lineaJson != null; 
                lineaJson = bufRead.readLine()) {
            
            textoJson.append(lineaJson);
        }
        bufRead.close();
        
        System.out.println(">>>> " + textoJson.toString().toUpperCase());
       
        Gson gson = new Gson();
        Producto producto = gson.fromJson(textoJson.toString(), Producto.class);
        
        System.out.println(">>>> " + producto.getNombre());
        
        /*producto.setNombre(producto.getNombre().toUpperCase());
        producto.setPrecio("5000 bolivares");*/
        sps = ServicioProductoSingleton.getInstancia();
        sps.modificar(producto);
        
        String jsonRespuesta = gson.toJson(producto);
        escritorRespuesta.println(jsonRespuesta);
        
        // ServicioProductoSingleton i = ServicioProductoSingleton.getInstancia();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter escritorRespuesta =  response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        //Producto obj1 = new Producto("abc", "3");
        //Producto obj2 = new Producto("gawk", "90");
        ArrayList<Producto> productos = sps.obtenerTodos();
        //List<Producto> list = Arrays.asList(obj1, obj2);
        
        JsonArray ja= new JsonArray();
        
        Gson gson = new Gson();
        
        for (Producto prod: productos){
            String json = gson.toJson(prod);
            ja.add(json);
        }
        
        
        
        //String json = gson.toJson(productos);
        escritorRespuesta.println(ja);
        
        /*
        PrintWriter escritorRespuesta =  response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();

        ArrayList<Producto> productos = sps.obtenerTodos();
        for (Producto producto: sps.obtenerTodos()){
        System.out.println(producto.getNombre());}
        Gson gsonBuilder = new GsonBuilder().create();
        
        String jsonRespuesta = gson.toJson(productos);
        escritorRespuesta.println(jsonRespuesta);*/
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException
    {
        
        PrintWriter escritorRespuesta = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        // Coge de un flujo:
        BufferedReader bufRead = request.getReader();
        StringBuilder textoJson = new StringBuilder();
        //String textoJson = "";
        // for (Inicializas; límite o fin; incremento)
        for (String lineaJson = bufRead.readLine(); lineaJson != null; lineaJson=bufRead.readLine()){
            //textoJson += lineaJson;
            textoJson.append(lineaJson);
        }
        //http://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html
        Gson g = new Gson();
        Producto producto = g.fromJson(textoJson.toString(), Producto.class);
        
        System.out.println(">>>> " + producto.getNombre());
        sps.insertar(producto);
        for(Producto prod : sps.obtenerTodos()) {
            System.out.println(prod.getNombre());
        }
    }
}
