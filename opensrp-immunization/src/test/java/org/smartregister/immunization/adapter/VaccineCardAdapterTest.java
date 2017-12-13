package org.smartregister.immunization.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.domain.Alert;
import org.smartregister.domain.AlertStatus;
import org.smartregister.immunization.BaseUnitTest;
import org.smartregister.immunization.customshadows.FontTextViewShadow;
import org.smartregister.immunization.db.VaccineRepo;
import org.smartregister.immunization.domain.Vaccine;
import org.smartregister.immunization.domain.VaccineData;
import org.smartregister.immunization.domain.VaccineTest;
import org.smartregister.immunization.domain.VaccineWrapper;
import org.smartregister.immunization.repository.VaccineRepository;
import org.smartregister.immunization.view.ImmunizationRowGroup;
import org.smartregister.immunization.view.VaccineGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by onaio on 30/08/2017.
 */
@Config(shadows = {FontTextViewShadow.class,ImageUtilsShadow.class,VaccineCardShadow.class})
public class VaccineCardAdapterTest extends BaseUnitTest {

    @Mock
    private Context context;

    private VaccineCardAdapter vaccineCardAdapter;

    private VaccineGroup view;

    @Mock
    private CommonPersonObjectClient commonPersonObjectClient;

    @Mock
    protected View convertView;

    @Mock
    protected ViewGroup parentView;

    private final int magicNumber = 231231;
    private JSONObject vaccineData;
    private CommonPersonObjectClient childdetails;
    private ArrayList<Vaccine> vaccinelist;
    private ArrayList<Alert> alertlist;
    private VaccineWrapper wrapper;
    private ArrayList<VaccineWrapper> wrappers;
    private final String magicDate = "1985-07-24T00:00:00.000Z";

    @Before
    public void setUp() throws Exception {
        view = new VaccineGroup(RuntimeEnvironment.application);
        setDataForTest(magicDate);
        vaccineCardAdapter = new VaccineCardAdapter(RuntimeEnvironment.application,view,"child");
        org.mockito.MockitoAnnotations.initMocks(this);
    }

    @Test
    public void assertConstructorsCreateNonNullObjectsOnInstantiation() throws JSONException {
        org.junit.Assert.assertNotNull(new VaccineCardAdapter(context, view ,""));
    }

    @Test
    public void assertGetCountReturnsTheCorrectNumberOfItems() throws Exception {

        org.junit.Assert.assertEquals(2, vaccineCardAdapter.getCount());

        //should return null
        junit.framework.Assert.assertNull(vaccineCardAdapter.getItem(0));

        junit.framework.Assert.assertEquals(vaccineCardAdapter.getItemId(0), magicNumber);

    }

    @Test
    public void assertGetViewReturnsVaccineCard() {
        junit.framework.Assert.assertEquals(vaccineCardAdapter.getView(0,null,null)!=null,true);
    }

    public void setDataForTest(String dateTimeString) throws Exception {
        wrappers = new ArrayList<VaccineWrapper>();
        wrapper = new VaccineWrapper();
        wrapper.setDbKey(0l);
        wrapper.setName(VaccineRepo.Vaccine.bcg2.display());
        wrapper.setVaccine(VaccineRepo.Vaccine.bcg2);
        wrappers.add(wrapper);
        wrapper = new VaccineWrapper();
        wrapper.setDbKey(0l);
        wrapper.setVaccine(VaccineRepo.Vaccine.opv1);
        wrapper.setName(VaccineRepo.Vaccine.opv1.display());
        wrappers.add(wrapper);
        wrapper = new VaccineWrapper();
        wrapper.setDbKey(0l);
        wrapper.setName(VaccineRepo.Vaccine.measles2.display());
        wrapper.setVaccine(VaccineRepo.Vaccine.measles2);
        wrappers.add(wrapper);
        JSONArray vaccineArray = new JSONArray(VaccineData.vaccines);
        vaccineData = vaccineArray.getJSONObject(0);
        HashMap<String, String> detail = new HashMap<String, String>();
        detail.put("dob", dateTimeString);
        detail.put("gender","male");
        detail.put("zeir_id","1");
        detail.put("first_name","");
        detail.put("last_name","");
        childdetails = new CommonPersonObjectClient("1", detail, "NME");
        childdetails.setColumnmaps(detail);
        Vaccine vaccine = new Vaccine(0l, VaccineTest.BASEENTITYID, VaccineRepo.Vaccine.measles2.display(), 0, new Date(),
                VaccineTest.ANMID, VaccineTest.LOCATIONID, VaccineRepository.TYPE_Synced, VaccineTest.HIA2STATUS, 0l, VaccineTest.EVENTID, VaccineTest.FORMSUBMISSIONID, 0);
        Alert alert = new Alert("", "", "", AlertStatus.complete, "", "");
        vaccinelist = new ArrayList<Vaccine>();
        vaccinelist.add(vaccine);
        vaccine = new Vaccine(0l, VaccineTest.BASEENTITYID, VaccineRepo.Vaccine.bcg2.display(), 0, new Date(),
                VaccineTest.ANMID, VaccineTest.LOCATIONID, VaccineRepository.TYPE_Synced, VaccineTest.HIA2STATUS, 0l, VaccineTest.EVENTID, VaccineTest.FORMSUBMISSIONID, 0);
        vaccinelist.add(vaccine);
        vaccine = new Vaccine(0l, VaccineTest.BASEENTITYID, VaccineRepo.Vaccine.opv1.display(), 0, new Date(),
                VaccineTest.ANMID, VaccineTest.LOCATIONID, VaccineRepository.TYPE_Synced, VaccineTest.HIA2STATUS, 0l, VaccineTest.EVENTID, VaccineTest.FORMSUBMISSIONID, 0);
        vaccinelist.add(vaccine);
        alertlist = new ArrayList<Alert>();
        alertlist.add(alert);
        view.setData(vaccineData, childdetails, vaccinelist, alertlist,"child");
    }

}
