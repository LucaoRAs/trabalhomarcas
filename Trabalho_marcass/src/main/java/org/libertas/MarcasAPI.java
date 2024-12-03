package org.libertas;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import com.google.gson.Gson;

/**
 * Servlet implementation class MarcasAPI
 */
//@WebServlet("/MarcasAPI/*")
public class MarcasAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MarcasAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MarcasDao mdao = new MarcasDao();
		Gson gson = new Gson();
		
		int id = 0;
		try {
			//pega o id passado por parametro 
			id = Integer.parseInt(request.getPathInfo().substring(1));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
				
		String pesquisa = request.getParameter("pesquisa");
		
		String resposta;
		if (id==0) {
			//listar todos
			resposta = gson.toJson(mdao.listar(pesquisa));
		} else {
			//consultar apenas 1
			resposta = gson.toJson(mdao.consultar(id));
		}
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//pega o body da request
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		//converte o body para um objeto Java
		Gson gson = new Gson();
		Marcas m = gson.fromJson(body,  Marcas.class);
		
		//salva a nova pessoa
		MarcasDao mdao = new MarcasDao();
//		pdao.inserir(p);
		
		
		
		//envia a resposta
		String resposta = gson.toJson(mdao.inserir(m));
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//pega o body da request
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		
		//converte o body para um objeto Java
		Gson gson = new Gson();
		Marcas m = gson.fromJson(body,  Marcas.class);
		
		//pega o id passado por parametro
		int id = 0;
		try {
			id = Integer.parseInt(request.getPathInfo().substring(1));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		m.setId(id);
		
		//salva a nova pessoa
		MarcasDao mdao = new MarcasDao();
		mdao.alterar(m);
				
		
		//envia a resposta
		String resposta = gson.toJson(mdao.alterar(m));
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);		
		
		
	}
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//pega o id passado por parametro
		int id = 0;
		try {
			id = Integer.parseInt(request.getPathInfo().substring(1));
					
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//exclui a nova pessoa
		MarcasDao mdao = new MarcasDao();
		Marcas m = new Marcas();
		Gson gson = new Gson();
		m.setId(id);
		mdao.excluir(m);
		
		//evia resposta
		
		String resposta = gson.toJson(mdao.excluir(m));
		response.setHeader("content-type", "application/json");
		response.getWriter().print(resposta);	
	}
}
	
	