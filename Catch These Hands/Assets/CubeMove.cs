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
        GameObject leftHand = GameObject.Find("RigidRoundHand_L");
        if (leftHand != null)
        {
            DataFetcher data = leftHand.GetComponent<DataFetcher>();
            this.transform.Translate((float)-data.roll / 800, 0, (float) data.pitch / 800);
        }
        System.IO.File.WriteAllText("../Light_Havoc/tmp.tmp", "testing one two three");

    }
}
