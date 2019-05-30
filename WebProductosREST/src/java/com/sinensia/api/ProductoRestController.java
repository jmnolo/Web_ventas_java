package com.sinensia.api;

import com.google.gson.Gson;
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
@WebServlet(asyncSupported = true, urlPatterns = "/api/productos")
public class ProductoRestController extends HttpServlet {

    private ServicioProductoSingleton sps;

    @Override
    public void init() {
        ServicioProductoSingleton sps = ServicioProductoSingleton.getInstancia();
    }

    @Override
    protected void doPut(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter escritorRespuesta = response.getWriter();
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

        sps = ServicioProductoSingleton.getInstancia();
        sps.modificar(producto);

        String jsonRespuesta = gson.toJson(producto);
        escritorRespuesta.println(jsonRespuesta);
    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        sps = ServicioProductoSingleton.getInstancia();
        for (Producto prod : sps.obtenerTodos()) {
            System.out.println(prod.getNombre());
        }

        PrintWriter escritorRespuesta = response.getWriter();
        response.setContentType("application/json;charset=UTF-8");

        ArrayList<Producto> productos = sps.obtenerTodos();

        Gson gson = new Gson();

        String json = gson.toJson(productos);
        escritorRespuesta.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        // Coge de un flujo:
        BufferedReader bufRead = request.getReader();
        StringBuilder textoJson = new StringBuilder();
        //String textoJson = "";
        // for (Inicializas; límite o fin; incremento)
        for (String lineaJson = bufRead.readLine(); lineaJson != null; lineaJson = bufRead.readLine()) {
            //textoJson += lineaJson;
            textoJson.append(lineaJson);
        }
        Gson g = new Gson();
        Producto producto = g.fromJson(textoJson.toString(), Producto.class);
        sps = ServicioProductoSingleton.getInstancia();
        System.out.println(">>>> " + producto.getNombre());
        sps.insertar(producto);

    }
}
