package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::registerUser);
        app.post("/login", this::loginUser);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getMessages);
        app.get("/messages/{message_id}", this::getMessageByID);
        app.delete("/messages/{message_id}", this::deleteMessageByID);
        app.patch("/messages/{message_id}", this::patchMessage);
        app.get("/accounts/{account_id}/messages", this::getAllAccountMessages);
        
        return app;
    }

    
    private void registerUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Account account = mObjectMapper.readValue(ctx.body(), Account.class);

        Account addedAccount = accountService.createAccount(account);

        if(addedAccount != null){
            ctx.json(addedAccount);
        }else{
            ctx.status(400);
        }
    }
    
    private void loginUser(Context ctx) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Account account = mObjectMapper.readValue(ctx.body(), Account.class);

        Account loginAccount = accountService.getAccount(account);

        if(loginAccount != null){
            ctx.json(loginAccount);
        }else{
            ctx.status(401);
        }
    }


    private void createMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Message message = mObjectMapper.readValue(ctx.body(), Message.class);

        Message newMsg = messageService.createMessage(message);

        if(newMsg != null){
            ctx.json(newMsg);
        }else{
            ctx.status(400);
        }
    }

    private void getMessages(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageByID(Context ctx) throws JsonProcessingException{
        Message msg = messageService.getMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if(msg != null){
            ctx.json(msg);
        }else{
            ctx.json("");
        }
    }

    private void deleteMessageByID(Context ctx) throws JsonProcessingException{
        Message msg = messageService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if(msg != null){
            ctx.json(msg);
        }else{
            ctx.json("");
        }
    }

    private void patchMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper(); //Jackson ObjectMapper
        Message m = mObjectMapper.readValue(ctx.body(), Message.class); 
        int id = Integer.parseInt(ctx.pathParam("message_id")); 

        Message updatedMessage = messageService.updateMessage(m.getMessage_text(), id); 

        if(updatedMessage != null){
            ctx.json(updatedMessage);
        }else{
            ctx.status(400);
        }
    }

    private void getAllAccountMessages(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllAccountMessages(Integer.parseInt(ctx.pathParam("account_id"))));
    }
}