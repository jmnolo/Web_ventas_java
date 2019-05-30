package com.sinensia.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        ServicioProductoSingleton sps = ServicioProductoSingleton.getInstancia();
        sps.insertar(producto);
    }
    
    //@Override
    protected void doGet2(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException
    {
        Producto p1 = new Producto("P1","30");
        Producto p2 = new Producto("P2","4");
        sps.insertar(p1);
        sps.insertar(p2);
        /*p1.setNombre("P1");
        p1.setPrecio("30 €");
        p2.setNombre("P2");
        p2.setPrecio("50 $");*/
        
        
        //
        //
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Producto[] productos = gson.fromJson(reader, Producto[].class);
        //
        
        PrintWriter escritorRespuesta =  response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        
        //
        String jsonRespuesta = gson.toJson(productos);
        escritorRespuesta.println(jsonRespuesta);
        // 
        
        
        /*BufferedReader bufRead = request.getReader();
        
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
        
        System.out.println(">>>> " + producto.getNombre());*/
        
        /*sps.obtenerTodos();
        Gson gson = new Gson();
        for (int i=0;i<sps.obtenerTodos().length;i++){
            Producto producto=sps.obtenerTodos()[i];
            String jsonRespuesta = gson.toJson(producto);
            System.out.println(producto);
            escritorRespuesta.println(jsonRespuesta);
        }*/
        
                
        // ServicioProductoSingleton i = ServicioProductoSingleton.getInstancia();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException
    {
        
        PrintWriter escritorRespuesta =  response.getWriter();
        response.setContentType("application/json;charset=UTF-8");
        
        Gson gson = new Gson();

        ArrayList<Producto> productos = sps.obtenerTodos();
        
        String jsonRespuesta = gson.toJson(productos);
        escritorRespuesta.println(jsonRespuesta);
    }
}
