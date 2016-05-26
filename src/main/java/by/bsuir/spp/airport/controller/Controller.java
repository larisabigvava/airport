package by.bsuir.spp.airport.controller;

import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.command.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Seagull on 21.04.2016.
 */
@WebServlet(name="controller", urlPatterns = "*.do")
public class Controller extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter("btn");
        req.setCharacterEncoding("UTF-8");
        switch (commandName){
            case "sign_in":{
                SignInCommand command = new SignInCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "registration":{
                RegistrationCommand command = new RegistrationCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "user_save_changes":{
                SaveUserChangesCommand command = new SaveUserChangesCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "add_airline":{
                AddAirlineCommand command = new AddAirlineCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "delete_airline":{
                DeleteAirlineCommand command = new DeleteAirlineCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "show_flights":{
                ShowFlightsCommand command = new ShowFlightsCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "search":{
                SearchCommand command = new SearchCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "reserve":{
                ReserveTicketCommand command = new ReserveTicketCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "add_flight":{
                AddFlightCommand command = new AddFlightCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "add_pilot":{
                AddPilotCommand command = new AddPilotCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "log_off":{
                LogOffCommand command = new LogOffCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "edit_pilot":{
                EditPilotCommand command = new EditPilotCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "delete_pilot":{
                DeletePilotCommand command = new DeletePilotCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "edit_flight":{
                EditFlightCommand command = new EditFlightCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "to_edit_flight":{
                ToEditFlightCommand command = new ToEditFlightCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
            case "to_edit_pilot":{
                ToEditPilotCommand command = new ToEditPilotCommand();
                try {
                    req.getRequestDispatcher(command.execute(req)).forward(req,resp);
                }catch (CommandException e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
