class Solution {
  public:
    int minMeetingRooms(vector<int> &start, vector<int> &end) {
        // code here
        vector<vector<int>> events;

        int i = 0, n = start.size();

        while (i < n){
            events.push_back({start[i], 1});
            events.push_back({end[i], -1});
            i++;
        }
        
        sort(events.begin(), events.end(), [](const auto &e1, const auto &e2){
            return (e1[0] != e2[0]) ? e1[0] < e2[0] : e1[1] < e2[1];
        });

        i = 0;

        int active = 0, rooms = 0;
        while(i < 2*n){
            active += events[i][1];
            if(active > rooms) rooms++;
            i++;
        }

        return rooms;
    }
};