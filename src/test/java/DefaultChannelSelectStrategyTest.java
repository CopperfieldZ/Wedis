import com.david.wedis.channel.DefaultChannelSelectStrategy;
import com.david.wedis.channel.LocalChannelOption;
import com.david.wedis.channel.epoll.EpollChannelOption;
import com.david.wedis.channel.kqueue.KqueueChannelOption;
import com.david.wedis.channel.select.NioSelectChannelOption;
import org.junit.Test;

public class DefaultChannelSelectStrategyTest {
    DefaultChannelSelectStrategy selectStrategy=new DefaultChannelSelectStrategy();
    @Test
    public void testChannelSelect(){
        LocalChannelOption localChannelOption= selectStrategy.select();
        System.out.println("KqueueChannelOption:"+(localChannelOption instanceof KqueueChannelOption));
        System.out.println("EpollChannelOption:"+(localChannelOption instanceof EpollChannelOption));
        System.out.println("NioSelectChannelOption:"+(localChannelOption instanceof NioSelectChannelOption));

    }
}
