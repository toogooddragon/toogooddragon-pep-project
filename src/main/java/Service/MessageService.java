package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message msg){
        if(msg.getMessage_text().length() <= 0 
            || msg.getMessage_text().length() >= 255
            || !messageDAO.accountExists(msg.getPosted_by())){
                return null;
        }
        return messageDAO.createMessage(msg);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessage(int id){
        return messageDAO.getMessage(id);
    }

    public Message updateMessage(String text, int id){
        if(text.length() <= 0 
            || text.length() >= 255
            || messageDAO.getMessage(id) == null){
                return null;
        }
        return messageDAO.updateMessage(text, id);
    }

    public Message deleteMessage(int id){
        return messageDAO.deleteMessage(id);
    }

    public List<Message> getAllAccountMessages(int accountID){
        return messageDAO.getAllAccountMessagesByID(accountID);
    }
}
