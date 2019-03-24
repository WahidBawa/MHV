using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class CubeMove : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        string s = "";
        GameObject leftHand = GameObject.Find("RigidRoundHand_L");
        GameObject rightHand = GameObject.Find("RigidRoundHand_R");

        if (leftHand != null)
        {
            DataFetcher data = leftHand.GetComponent<DataFetcher>();
            this.transform.Translate((float)-data.roll / 800, 0, (float) data.pitch / 800);
            s += -data.roll + "," + data.pitch;
        } else
        {
            s +="0,0";
        }
        s += " ";
        if (rightHand != null)
        {
            Rotation data = rightHand.GetComponent<Rotation>();

            this.transform.Rotate(0, (float)(data.angle), 0);
            s += data.angle;
            s += " " + data.pewing;
        }
        else
        {
            s += "0 0";
        }

        System.IO.File.WriteAllText("../Light_Havoc/tmp.tmp", s);

    }
}
