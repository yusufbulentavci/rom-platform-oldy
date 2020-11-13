package com.bilgidoku.rom.protokol.protocols.future;

import java.util.ArrayList;
import java.util.List;

import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.ilk.json.JSONObject;
import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;
import com.bilgidoku.rom.protokol.protocols.Response;


public class FutureResponseImpl implements FutureResponse{
    private final static MC mc=new MC(FutureResponseImpl.class);

    public FutureResponseImpl() {
    }
    
    
    protected Response response;
    private List<ResponseListener> listeners;
    private int waiters;

    protected final synchronized void checkReady() {
        while (!isReady()) {
            try {
                waiters++;
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                waiters--;
            }
        }
    }
    public synchronized void addListener(ResponseListener listener) {
        if (isReady()) {
            listener.onResponse(this);
        } else {
            if (listeners == null) {
                listeners = new ArrayList<ResponseListener>();
            }
            listeners.add(listener);
        }
    }

    public synchronized void removeListener(ResponseListener listener) {
        if (!isReady()) {
            if (listeners != null) {
                listeners.remove(listener);
            }
        }
    }

    public synchronized boolean isReady() {
        return response != null;
    }
    
    public List<CharSequence> getLines() {
        checkReady();
        return response.getLines();
    }


    public String getRetCode() {
        checkReady();
        return response.getRetCode();
    }


    public boolean isEndSession() {
        checkReady();
        return response.isEndSession();
    }

    @Override
    public synchronized String toString() {
        checkReady();
        return response.toString();
    }
    
    private final static Astate resperr=mc.c("future-on-response");
    /**
     * Set the {@link Response} which will be used to notify the registered
     * {@link ResponseListener}'. After this method is called all waiting
     * threads will get notified and {@link #isReady()} will return <code>true<code>. 
     * 
     * @param response
     */
    public void setResponse(Response response) {
        boolean fire = false;
        synchronized (this) {
            if (!isReady()) {
                this.response = response;
                fire = listeners != null;

                if (waiters > 0) {
                    notifyAll();
                }
            }
        }

        if (fire) {
            for (ResponseListener listener : listeners) {
                try {
                    listener.onResponse(this);
                } catch (Throwable e) {
                	resperr.more(e);
                }
            }
            listeners = null;
            
        }
    }
	@Override
	public boolean isSuccess() {
		checkReady();
        return response.isSuccess();
	}
	@Override
	public boolean isImportant() {
		return response.isImportant();
	}
	@Override
	public JSONObject toReport() throws JSONException {
		return response.toReport();
	}

}
