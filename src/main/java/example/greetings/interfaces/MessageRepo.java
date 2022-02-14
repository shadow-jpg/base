package example.greetings.interfaces;

import example.greetings.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo  extends CrudRepository<Message, Integer> {
    List<Message> findByName(String name);
}
