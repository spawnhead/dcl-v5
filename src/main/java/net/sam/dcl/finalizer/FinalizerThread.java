package net.sam.dcl.finalizer;

import net.sam.dcl.log.Log;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.LogFactory;

/**
 * @author DG
 *         Date: 25.10.2008
 *         Time: 20:38:15
 */
public class FinalizerThread<T> extends Thread {
	protected static org.apache.commons.logging.Log log = LogFactory.getLog(FinalizerThread.class);
	static private FinalizerThread finalizerThread = new FinalizerThread();
	ReferenceQueue<? super T> queue = new ReferenceQueue<T>();
	List<FinalizerReference<T>> refs = new ArrayList<FinalizerReference<T>>();
	boolean stop = false;

	private FinalizerThread() {
		super("FINALIZER");
	}

	static public FinalizerThread get() {
		return finalizerThread;
	}

	public void run() {
    // сразу останавливаемся - потому что есть подозрение что временные
    // файлы удаляются раньше чем надо
    stopExecution();
		while (!stop) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				stop = true;
			}
			FinalizerReference<T> ref;
			while ((ref = (FinalizerReference) queue.poll()) != null) {
				log.info("Destroing:"+ref.toString());
				System.out.println("Destroing:"+ref.toString());
				ref.destroy();
				ref.clear();
				synchronized (refs){
					refs.remove(ref);
				}
			}
		}
	}

	public void add(FinalizerReference ref) {
		synchronized (refs){
			refs.add(ref);
		}
	}
	public void stopExecution(){
		stop = true;	
	}

	static class Test{
		long num;
		long createdTime = System.currentTimeMillis();
		long arr[] = new long[100000];

		Test(long num) {
			this.num = num;
		}


		protected void finalize() throws Throwable {
			System.out.println("Finalize:"+num+":"+createdTime+","+ System.currentTimeMillis());
		}
	}
	public static void main(String[] args) throws InterruptedException {
		FinalizerThread ft = FinalizerThread.get();
		ft.start();
		add();
		ft.join(10000);
		ft.stopExecution();
	}
	static void add(){
		FinalizerThread ft = FinalizerThread.get();
		for (int i = 0; i < 10; i++){
			ft.add(new TestReference(new Test(i)));
		}

	}
	static public class TestReference extends FinalizerReference<Test> {
		long num;
		long createdTime = System.currentTimeMillis();

		public TestReference(Test referent) {
			super(referent);
			num = referent.num;
			createdTime = referent.createdTime;
		}

		public void destroy() {
			System.out.println("Destroy:"+num+":"+createdTime+","+ System.currentTimeMillis());
		}
	}
}
